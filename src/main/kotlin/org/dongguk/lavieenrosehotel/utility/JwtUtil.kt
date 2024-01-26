package org.dongguk.lavieenrosehotel.utility

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.AccessLevel
import lombok.Getter
import org.dongguk.lavieenrosehotel.constraint.Constants
import org.dongguk.lavieenrosehotel.dto.response.JwtTokenDto
import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
@Getter(AccessLevel.PROTECTED)
class JwtUtil(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.access-token-validity-in-seconds}")
    val accessTokenExpiration: Int,
    @Value("\${jwt.refresh-token-validity-in-seconds}")
    val refreshTokenExpiration: Int,
) {
    private val key: Key by lazy {
        val keyBytes = Decoders.BASE64.decode(secretKey);
        Keys.hmacShaKeyFor(keyBytes);
    }

    fun generateJwtTokens(id: Long, role: ERole): JwtTokenDto {
        return JwtTokenDto(
            accessToken = generateToken(id, role, accessTokenExpiration * 1000),
            refreshToken = generateToken(id, null, refreshTokenExpiration * 1000),
        )
    }

    fun validateToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    private fun generateToken(id: Long, role: ERole?, expirationPeriod: Int): String {
        val claims = Jwts.claims()
        claims[Constants.USER_ID_CLAIM_NAME] = id.toString()
        claims[Constants.USER_ROLE_CLAIM_NAME] = role?.value

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationPeriod))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}