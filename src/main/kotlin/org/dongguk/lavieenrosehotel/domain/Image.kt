package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "url")
    private var _url: String,

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private var _category: Category? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id")
    private var _amenity: Amenity? = null,
) {
    val id: Long?
        get() = _id
    val url: String
        get() = _url
    val category: Category?
        get() = _category
    val amenity: Amenity?
        get() = _amenity

    companion object {
        fun fromJson(url: String, category: Category?, amenity: Amenity?): Image {
            return Image(
                _url = url,
                _category = category,
                _amenity = amenity,
            )
        }
    }
}