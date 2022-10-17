package com.lambs.lambsroad.domain.user.dto

data class UserDeleteDto(
    val id: Long,
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
) {}
