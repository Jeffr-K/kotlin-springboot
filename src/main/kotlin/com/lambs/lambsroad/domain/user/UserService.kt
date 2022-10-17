package com.lambs.lambsroad.domain.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository
//    @Autowired
//    private lateinit var passwordEncoder: PasswordEncoder

    fun create(user: User): User {
//        var encodedPassword = passwordEncoder.encode(user.password)
//        user.password = encodedPassword
        return userRepository.save(user)
    }

    fun update(user: User): User {
        return userRepository.save(user)
    }

    fun delete(user: User) {
        return userRepository.delete(user);
    }

    fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    fun getUsers(): MutableIterable<User> {
        return userRepository.findAll()
    }

//    fun validatePassword(user: User): Boolean {
//        var member = this.getUser(user.id) ?: return false
//        if (!passwordEncoder.matches(user.password, member.password)) {
//            return false
//        }
//        return true
//    }

}
