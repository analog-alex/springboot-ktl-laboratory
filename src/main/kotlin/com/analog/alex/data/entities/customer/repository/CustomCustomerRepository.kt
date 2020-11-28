package com.analog.alex.data.entities.customer.repository

import com.analog.alex.data.entities.customer.model.Customer
import com.analog.alex.data.entities.user.model.Role
import com.analog.alex.data.entities.user.model.User
import com.analog.alex.data.utils.Constants
import com.analog.alex.data.utils.oneIfNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface CustomCustomerRepository {
    fun findByFields(name: String?, age: Int?, country: String?): Collection<Customer>
    fun findByUsername(username: String): Customer?
}

@Repository
class CustomCustomerRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : CustomCustomerRepository {

    private val rowMapperWithUser = RowMapper { rs, _ ->
        Customer(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            age = rs.getInt("age"),
            country = rs.getString("country"),
            user = User(
                id = rs.getLong("app_user"),
                username = rs.getString("username"),
                password = "******",
                role = Role.valueOf(rs.getString("role")),
                customer = null
            )
        )
    }

    override fun findByFields(name: String?, age: Int?, country: String?): Collection<Customer> {
        val query = """
            SELECT c.id, c.name, c.age, c.country, c.app_user, u.username, u.role FROM customers c 
            INNER JOIN app_users u ON u.id = c.app_user 
            WHERE (name = :name OR 1 = :isName) AND (age = :age OR 1 = :isAge) AND (country = :country OR 1 = :isCountry) 
        """

        val namedParameters = MapSqlParameterSource().also { map ->
            map.addValue("name", name ?: Constants.EMPTY)
            map.addValue("isName", name.oneIfNull())
            map.addValue("age", age ?: Constants.NEGATIVE_ONE)
            map.addValue("isAge", age.oneIfNull())
            map.addValue("country", country ?: Constants.EMPTY)
            map.addValue("isCountry", country.oneIfNull())
        }

        return namedParameterJdbcTemplate.query(query.trimIndent(), namedParameters, rowMapperWithUser)
    }

    override fun findByUsername(username: String): Customer? {
        val query = """
            SELECT c.id, c.name, c.age, c.country, c.app_user, u.username, u.role FROM customers c 
            INNER JOIN app_users u ON u.id = c.app_user 
            WHERE u.username = ?
        """

        return jdbcTemplate.query(query.trimIndent(), arrayOf(username), rowMapperWithUser).firstOrNull()
    }
}