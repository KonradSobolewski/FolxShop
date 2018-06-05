package com.konrad.folxshop.dao

import com.konrad.folxshop.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDao : JpaRepository<User,Long>