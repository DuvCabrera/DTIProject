package com.duv.internproject.service

import com.duv.internproject.model.Reminder
import com.duv.internproject.repository.ReminderRepository
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class ReminderServiceImpl(private val repository: ReminderRepository) : ReminderService {
    override fun create(reminder: Reminder): Reminder {
        Assert.hasLength(reminder.date, "[date] não pode estar em branco")
        Assert.hasLength(reminder.description, "[description] não pode estar em branco")
        Assert.isTrue(reminder.date.length == 10, "[date] precisa estar no formato correto dd/mm/aaaa")
        return repository.save(reminder)
    }

    override fun getAll(): List<Reminder> {
        return repository.findAll()
    }

    override fun getById(id: Long): Optional<Reminder> {
        return repository.findById(id)
    }

    override fun update(id: Long, reminder: Reminder): Optional<Reminder> {
        val optional = getById(id)
        if (!optional.isPresent) Optional.empty<Reminder>()

        return optional.map {
            val reminderUpdated = it.copy(
                date = reminder.date,
                description = reminder.description
            )
            repository.save(reminderUpdated)
        }
    }

    override fun delete(id: Long)  {
         getById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException()}

    }
}