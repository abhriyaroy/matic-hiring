package com.example.maticnetwork.presenter

import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterView
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterPresenterImpl
import com.example.maticnetwork.utils.CryptoCurrencies
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecyclerAdapterPresenterTest {

  @Mock
  private lateinit var recyclerItemViewMock: RecyclerAdapterView
  private val recyclerAdapterPresenter = RecyclerAdapterPresenterImpl()

  @Test
  fun `should show dialog on handleItemLongClick call success`() {
    CryptoCurrencies.getImagesList().forEachIndexed { position, item ->
      recyclerAdapterPresenter.handleItemLongClick(recyclerItemViewMock, position)

      verify(recyclerItemViewMock).showDialog(item.first, item.second)
    }
  }

  @Test
  fun `should return item count on handleGetItemCountCall call success`() {
    val result = recyclerAdapterPresenter.handleGetItemCountCall()

    assertEquals(CryptoCurrencies.getImagesList().size, result)
  }

  @Test
  fun `should show dialog on handleBindViewHolderCall call success`() {
    CryptoCurrencies.getImagesList().forEachIndexed { position, item ->
      recyclerAdapterPresenter.handleBindViewHolderCall(recyclerItemViewMock, position)

      verify(recyclerItemViewMock).populateView(
        item.first,
        item.second,
        item.third,
        (position * 100 - (position * 10)).toString()
      )
    }
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(recyclerItemViewMock)
  }
}