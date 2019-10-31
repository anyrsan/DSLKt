package com.any.org.eanewsmudle.viewpresenter.vpresenter

import com.any.org.eanewsmudle.viewpresenter.LoadRefreshListener
import com.any.org.eanewsmudle.viewpresenter.NDViewClickListener
import com.any.org.eanewsmudle.viewpresenter.OnScrollListener

/**
 *
 * @author any
 * @time 2019/10/31 17.05
 * @details
 */
class NewPresenter(
    val clickListener: NDViewClickListener,
    val loadRefreshListener: LoadRefreshListener,
    val onScrollListener: OnScrollListener
)