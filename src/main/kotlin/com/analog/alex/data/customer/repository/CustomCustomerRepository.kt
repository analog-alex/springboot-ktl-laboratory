package com.analog.alex.data.customer.repository

import com.analog.alex.data.customer.model.Customer
import com.analog.alex.data.user.model.User
import com.analog.alex.data.utils.intBasedBool
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

interface CustomCustomerRepository {
    fun findByFields(name: String?, age: Int?, country: String?): Collection<Customer>
}

@Repository
class CustomCustomerRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate
) : CustomCustomerRepository {

    override fun findByFields(name: String?, age: Int?, country: String?): Collection<Customer> {
        var query = """
            SELECT c.id, c.name, c.age, c.country, c.app_user, u.username, u.role FROM customers c 
            INNER JOIN app_users u ON u.id = c.app_user 
        """.trimIndent()

        var args = arrayOf<Any>()

        if (listOfNotNull(name, age, country).isNotEmpty()) {
            query += """
                WHERE (name = ? OR 1 = ?) AND (age = ? OR 1 = ?) AND (country = ? OR 1 = ?) 
            """.trimIndent()

            args = arrayOf(
                name ?: "", name.intBasedBool(),
                age ?: -1, age.intBasedBool(),
                country ?: "", country.intBasedBool()
            )
        }

        return jdbcTemplate.query(query, args) { rs, _ ->
            Customer(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                age = rs.getInt("age"),
                country = rs.getString("country"),
                user = User(
                    id = rs.getLong("app_user"),
                    username = rs.getString("username"),
                    password = "******",
                    role = rs.getString("role"),
                    customer = null
                )
            )
        }
    }
}