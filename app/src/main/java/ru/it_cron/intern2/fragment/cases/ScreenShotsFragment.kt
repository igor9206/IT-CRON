package ru.it_cron.intern2.fragment.cases

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.terrakok.cicerone.Router
import jp.shts.android.storiesprogressview.StoriesProgressView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.adapter.case.ScreenShotAdapter
import ru.it_cron.intern2.adapter.case.ZoomOutPageTransformer
import ru.it_cron.intern2.databinding.FragmentScreenShotsBinding
import ru.it_cron.intern2.viewmodel.CaseViewModel

class ScreenShotsFragment : Fragment() {

    private val caseViewModel: CaseViewModel by activityViewModel()
    private val router by inject<Router>()
    private var _binding: FragmentScreenShotsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ScreenShotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenShotsBinding.inflate(inflater, container, false)

        caseViewModel.detailCase.observe(viewLifecycleOwner) {
            if (it.case != null) {
                adapter = ScreenShotAdapter(it.case.data.images)
                with(binding) {
                    vpScreenShots.adapter = adapter
                    constraintLayout.setBackgroundColor(Color.parseColor("#${it.case.data.caseColor}"))
                    progressStories.apply {
                        setStoriesCount(it.case.data.images.size)
                        setStoryDuration(3000L)
                    }
                }

            }
        }

        with(binding) {
            buttonClose.setOnClickListener { router.exit() }

            vpScreenShots.setPageTransformer(ZoomOutPageTransformer())
            vpScreenShots.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                    binding.progressStories.apply {
                        reverse()
                        startStories(position)
                    }

                    binding.progressStories.setStoriesListener(object :
                        StoriesProgressView.StoriesListener {
                        override fun onNext() {
                            binding.vpScreenShots.currentItem = position + 1
                        }

                        override fun onPrev() {}

                        override fun onComplete() {
                            router.exit()
                        }
                    })
                }
            })
        }

        return binding.root
    }

    override fun onDestroyView() {
        binding.progressStories.destroy()
        super.onDestroyView()
        _binding = null
    }

}