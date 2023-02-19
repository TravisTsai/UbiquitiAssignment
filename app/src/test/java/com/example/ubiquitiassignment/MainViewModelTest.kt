package com.example.ubiquitiassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ubiquitiassignment.entity.AirPollutionVO
import com.example.ubiquitiassignment.entity.Resource
import com.example.ubiquitiassignment.entity.StatusEnum
import com.example.ubiquitiassignment.repository.RemoteRepository
import com.example.ubiquitiassignment.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutinesTestRule()

    private val goodAirPollutionsOb: Observer<Resource<List<AirPollutionVO>>> = mockk(relaxed = true)
    private val badAirPollutionsOb: Observer<List<AirPollutionVO>> = mockk(relaxed = true)

    private val fakeRepository: RemoteRepository = mockk(relaxed = true)
    private lateinit var viewModel: MainViewModel

    private val fakeList: List<AirPollutionVO> = listOf(
        AirPollutionVO(
            siteId = "20",
            siteName = "平鎮",
            pm25 = "6",
            county = "桃園市",
            status = StatusEnum.GOOD
        ), AirPollutionVO(
            siteId = "33",
            siteName = "彰化",
            pm25 = "76",
            county = "彰化市",
            status = StatusEnum.HARM
        ), AirPollutionVO(
            siteId = "12",
            siteName = "中山",
            pm25 = "10",
            county = "臺北市",
            status = StatusEnum.GOOD
        )
    )


    @Before
    fun setup() {
        viewModel = MainViewModel(fakeRepository)
        viewModel.goodAirPollutions.observeForever(goodAirPollutionsOb)
        viewModel.badAirPollutions.observeForever(badAirPollutionsOb)
    }

    @Test
    fun `Should FilterCorrectGoodSites When fetchDataWithThreshold`() {
        coEvery { fakeRepository.getAirPollutionData() } returns fakeList

        viewModel.fetchAirPollutions()

        verify(exactly = 2) { goodAirPollutionsOb.onChanged(any()) }

        val data = (viewModel.goodAirPollutions.value as? Resource.Success)?.data
        assertEquals(2, data?.size)
        assertEquals("20", data?.firstOrNull()?.siteId)
        assertEquals("12", data?.get(1)?.siteId)
    }

    @Test
    fun `Should FilterCorrectBadSites When fetchDataWithThreshold`() {
        coEvery { fakeRepository.getAirPollutionData() } returns fakeList

        viewModel.fetchAirPollutions()

        verify(exactly = 1) { badAirPollutionsOb.onChanged(any()) }

        assertEquals(1, viewModel.badAirPollutions.value?.size)
        assertEquals("33", viewModel.badAirPollutions.value?.firstOrNull()?.siteId)
    }

    @Test
    fun `Should HandleTheErrorCase When DataSourceIsCorrupt`() {
        val errorMessage = "api error!"
        coEvery { fakeRepository.getAirPollutionData() } throws IllegalArgumentException(errorMessage)

        viewModel.fetchAirPollutions()

        verify(exactly = 2) { goodAirPollutionsOb.onChanged(any()) }

        val actual = viewModel.goodAirPollutions.value as? Resource.Error
        assertEquals(errorMessage, actual?.message)
    }

}
