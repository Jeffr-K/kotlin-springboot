package com.lambs.lambsroad.domain.user.dto

data class UserCreateDto(
    val username: String,
    val password: String,
    val email: String,
    val phone: String
) {}
