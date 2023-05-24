package com.duv.internproject

import com.duv.internproject.model.Reminder
import com.duv.internproject.repository.ReminderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ReminderControllerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var reminderRepository: ReminderRepository

    @Test
    fun `test find all`(){
        reminderRepository.save(Reminder(date="11/11/1111", description = "Esse ano ja foi faz tempo"))

        mockMvc.perform(MockMvcRequestBuilders.get("/reminders"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].date").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].description").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`(){
        val reminder = reminderRepository.save(Reminder(date="11/11/1111", description = "Esse ano ja foi faz tempo"))

        mockMvc.perform(MockMvcRequestBuilders.get("/reminders/${reminder.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(reminder.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.date").value(reminder.date))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(reminder.description))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create reminder`(){
        val reminder = Reminder(date="11/11/1111", description = "Esse ano ja foi faz tempo")
        reminderRepository.deleteAll()
        val json = ObjectMapper().writeValueAsString(reminder)
        mockMvc.perform(MockMvcRequestBuilders.post("/reminders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.date").value(reminder.date))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(reminder.description))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(reminderRepository.findAll().isEmpty())
    }

    @Test
    fun `test  update`(){
        val reminder = reminderRepository
            .save(Reminder(date="11/11/1111", description = "Esse ano ja foi faz tempo"))
            .copy(date= "21/12/2023")

        val json = ObjectMapper().writeValueAsString(reminder)
        mockMvc.perform(MockMvcRequestBuilders.put("/reminders/${reminder.id}")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.date").value(reminder.date))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(reminder.description))
            .andDo(MockMvcResultHandlers.print())

        val findById = reminderRepository.findById(reminder.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(reminder.date, findById.get().date)
    }

    @Test
    fun `test delete reminder`(){
        val reminder = reminderRepository.save(
            Reminder(date="11/11/1111", description = "Esse ano ja foi faz tempo"))
            .copy(date= "21/12/2023")

        mockMvc.perform(MockMvcRequestBuilders.delete("/reminders/${reminder.id}")
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = reminderRepository.findById(reminder.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    @Test
    fun `test create reminder error empty date`(){
        val reminder = Reminder(date="", description = "Esse ano ja foi faz tempo")
        reminderRepository.deleteAll()
        val json = ObjectMapper().writeValueAsString(reminder)
        mockMvc.perform(MockMvcRequestBuilders.post("/reminders")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[date] não pode estar em branco"))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun `test create reminder error date must have 10 characters`(){
        val reminder = Reminder(date="1/10/1511", description = "Esse ano ja foi faz tempo")
        reminderRepository.deleteAll()
        val json = ObjectMapper().writeValueAsString(reminder)
        mockMvc.perform(MockMvcRequestBuilders.post("/reminders")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[date] precisa estar no formato correto dd/mm/aaaa"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create reminder error empty description`(){
        val reminder = Reminder(date="10/10/2010", description = "")
        reminderRepository.deleteAll()
        val json = ObjectMapper().writeValueAsString(reminder)
        mockMvc.perform(MockMvcRequestBuilders.post("/reminders")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[description] não pode estar em branco"))
            .andDo(MockMvcResultHandlers.print())

    }
}