package com.lambs.lambsroad.domain.user

import com.lambs.lambsroad.domain.user.dto.UserCreateDto
import com.lambs.lambsroad.domain.user.dto.UserDeleteDto
import com.lambs.lambsroad.domain.user.dto.UserUpdateDto
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var modelMapper: ModelMapper

    @PostMapping("/user")
    fun createUser(@RequestBody userCreateDto: UserCreateDto): ResponseEntity<Any> {
        val result = userService.create(modelMapper.map(userCreateDto, User::class.java))
        return ResponseEntity.ok().body(result)
    }

    @PutMapping("/user")
    fun updateUser(@RequestBody userUpdateDto: UserUpdateDto): ResponseEntity<Any> {
        val result = userService.update(modelMapper.map(userUpdateDto, User::class.java))
        return ResponseEntity.ok().body(result)
    }

    @DeleteMapping("/user")
    fun deleteUser(@RequestBody userDeleteDto: UserDeleteDto): ResponseEntity<Any> {
        val result = userService.delete(modelMapper.map(userDeleteDto, User::class.java))
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<Any> {
        val result = userService.getUser(id)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<MutableIterable<User>> {
        val result = userService.getUsers()
        return ResponseEntity.ok().body(result)
    }
}
