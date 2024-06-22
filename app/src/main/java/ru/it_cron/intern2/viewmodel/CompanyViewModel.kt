package ru.it_cron.intern2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.it_cron.intern2.dto.Testimonial
import ru.it_cron.intern2.model.TestimonialModel
import ru.it_cron.intern2.repository.Repository

class CompanyViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _testimonials = MutableLiveData<TestimonialModel>()
    val testimonials: LiveData<TestimonialModel> = _testimonials

    private val defaultShowSize = 3
    private val showSize = MutableLiveData(defaultShowSize)
    val portionTestimonials: LiveData<List<Testimonial.Data>>
        get() = showSize.switchMap { count ->
            _testimonials.map {
                it.testimonial?.data!!.take(count.coerceAtMost(it.testimonial.data.size))
            }
        }

    init {
        getTestimonials()
    }

    private fun getTestimonials() = viewModelScope.launch {
        _testimonials.value = TestimonialModel(load = true)
        repository.getTestimonials()
            .onSuccess {
                _testimonials.value = TestimonialModel(testimonial = it)
            }
            .onFailure {
                _testimonials.value = TestimonialModel(error = true)
            }
    }

    fun showMore() {
        showSize.value = showSize.value?.let {
            it + defaultShowSize
        }
    }
}