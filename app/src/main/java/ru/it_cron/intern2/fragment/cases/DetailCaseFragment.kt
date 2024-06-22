package ru.it_cron.intern2.fragment.cases

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.adapter.case.CaseImageAdapter
import ru.it_cron.intern2.adapter.case.OnCaseImageClickListener
import ru.it_cron.intern2.databinding.CaseCardFeaturesItemBinding
import ru.it_cron.intern2.databinding.FragmentDetailCaseBinding
import ru.it_cron.intern2.extension.loadImage
import ru.it_cron.intern2.model.DetailCaseModel
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.util.Intents
import ru.it_cron.intern2.viewmodel.CaseViewModel

class DetailCaseFragment : Fragment() {

    private val caseViewModel: CaseViewModel by activityViewModel()
    private val router by inject<Router>()
    private var _binding: FragmentDetailCaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: CaseImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdapter = CaseImageAdapter(object : OnCaseImageClickListener {
            override fun open() {
                router.navigateTo(Screens.screenShotsFragment())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCaseBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener { router.exit() }

        caseViewModel.detailCase.observe(viewLifecycleOwner) { detailCaseModel ->
            binding.scrollView.isVisible = !detailCaseModel.load
            when (detailCaseModel.load) {
                true -> binding.progressBar.show()
                else -> binding.progressBar.hide()
            }
            setContent(detailCaseModel)
        }

        caseViewModel.navigationEvent.observe(viewLifecycleOwner) {
            when (it) {
                NavigationCommand.ToErrorNetworkFragment -> {
                    router.replaceScreen(Screens.errorInternetFragment())
                }

                else -> {}
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setContent(detailCaseModel: DetailCaseModel) {
        with(binding) {
            if (detailCaseModel.case != null) {
                tvNameCase.text = detailCaseModel.case.data.title
                ivCaseImage.loadImage(detailCaseModel.image)

                task.text = detailCaseModel.case.data.task

                rvImage.apply {
                    adapter = rvAdapter
                    detailCaseModel.case.data.images.isNotEmpty()
                    setBackgroundColor(
                        Color.parseColor("#${detailCaseModel.case.data.caseColor}")
                    )
                }
                rvAdapter.submitList(detailCaseModel.case.data.images)

                featuresTitle.apply {
                    isVisible = !detailCaseModel.case.data.featuresTitle.isNullOrEmpty()
                    text = detailCaseModel.case.data.featuresTitle
                }
                containerFeaturesDone.apply {
                    removeAllViews()
                    isVisible = !detailCaseModel.case.data.featuresTitle.isNullOrEmpty()

                }
                detailCaseModel.case.data.featuresDone?.forEach {
                    CaseCardFeaturesItemBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        binding.containerFeaturesDone,
                        true
                    ).apply {
                        content.text = it
                    }.root
                }

                cardTechnologiesAndPlatform.setCardBackgroundColor(
                    Color.parseColor("#${detailCaseModel.case.data.caseColor}")
                )
                tvTechnologies.text = detailCaseModel.case.data.technologies.joinToString {
                    "${it.name} "
                }
                tvPlatforms.text = detailCaseModel.case.data.platforms.joinToString {
                    "${it.name} "
                }

                buttonGooglePlay.apply {
                    isVisible = !detailCaseModel.case.data.androidUrl.isNullOrEmpty()
                    setOnClickListener {
                        detailCaseModel.case.data.androidUrl?.let {
                            Intents.browserIntent(it, requireContext())
                        }
                    }
                }
                buttonAppStore.apply {
                    isVisible = !detailCaseModel.case.data.iOSUrl.isNullOrEmpty()
                    setOnClickListener {
                        detailCaseModel.case.data.iOSUrl?.let {
                            Intents.browserIntent(it, requireContext())
                        }
                    }
                }
                buttonWebSite.apply {
                    isVisible = !detailCaseModel.case.data.webUrl.isNullOrEmpty()
                    setOnClickListener {
                        detailCaseModel.case.data.webUrl?.let {
                            Intents.browserIntent(it, requireContext())
                        }
                    }
                }

                tvEmailItCron.setOnClickListener {
                    Intents.emailIntent(
                        requireContext(),
                        Constants.EMAIL_IT_CRON
                    )
                }

                buttonApplication.setOnClickListener {
                    router.navigateTo(Screens.applicationFragment())
                }

                nextCaseTitle.isVisible = detailCaseModel.nextCase != null
                cardNextCase.apply {
                    isVisible = detailCaseModel.nextCase != null
                    setOnClickListener {
                        detailCaseModel.nextCase?.let {
                            caseViewModel.getCaseById(it)
                        }
                        router.replaceScreen(Screens.detailCaseFragment())
                    }
                }
                ivNextCase.loadImage(detailCaseModel.nextCase?.image)

                tvBackToMainPage.setOnClickListener {
                    router.navigateTo(Screens.mainFragment())
                }

            }
        }
    }

}