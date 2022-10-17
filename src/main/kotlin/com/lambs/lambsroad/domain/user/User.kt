package com.lambs.lambsroad.domain.user

import com.lambs.lambsroad.domain.user.Role
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class User(
                @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                val username: String,
                var password: String,
                val email: String,
                val phone: String,
                @CreationTimestamp
                @Column(nullable = false, updatable = false)
                val createDateTime: OffsetDateTime = OffsetDateTime.now(),
                @CreationTimestamp
                @Column(nullable = false, updatable = false)
                var updateDateTime: OffsetDateTime? = null,
                @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
                @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
                var roles: Set<Role>? = null
) {}

