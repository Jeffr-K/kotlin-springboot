package com.lambs.lambsroad.domain.user

import javax.persistence.*

@Entity
data class Role(
                @Id
                @GeneratedValue
                val id: Int,
                @Column()
                var roleName: String? = null,
                @Column()
                var description: String? = null,
                @ManyToMany(mappedBy = "roles")
                var users: MutableSet<User>? = null
)
