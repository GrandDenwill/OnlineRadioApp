package com.example.onlineradioapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineradioapp.R
import com.example.onlineradioapp.adapter.RadioViewModel
import com.example.onlineradioapp.databinding.FragmentRosterBinding
import com.example.onlineradioapp.adapter.RadioAdapter
import com.example.onlineradioapp.repo.RadioModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


@AndroidEntryPoint
class RadioListFragment : Fragment(){
    private var binding: FragmentRosterBinding? = null
    private var currentRadioStation: RadioModel?=null
    val vm: RadioViewModel by viewModels()
    private var player: RadioPlayer? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_roster,menu)
      super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRosterBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.actions_roster, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search -> {
                        // clearCompletedTasks()
                        true
                    }

                    R.id.favorite -> {
                        // loadTasks(true)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val adapter = RadioAdapter(
            layoutInflater,
            ::playMusic
        )
        binding?.items?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL)
            )
            this.adapter = adapter
        }
        /*viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                vm.states.collect { state ->
                    adapter.submitList(state.items)
                }
            }*/
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vm.states.collect { state ->
                    adapter.submitList(state.items)
                }
            }
        }
            currentRadioStation = vm.currentRadioStation
            player = vm.radioPlayer
            if (currentRadioStation !== null) {
                binding?.apply {
                    if (!player?.mediaPlayer!!.isPlaying) {
                        playButton.background =
                            activity?.let { ContextCompat.getDrawable(it, R.drawable.play_icon) };
                    } else {
                        playButton.background =
                            activity?.let { ContextCompat.getDrawable(it, R.drawable.play_icon) };
                }
            }
            showMusic()
        }
    }
    @OptIn(UnstableApi::class) private fun playMusic(radio: RadioModel){
        player?.playMusic(radio,::showMusic)
        currentRadioStation = radio
    }
    private fun onPlayButton(){
        binding?.apply {
            if (player?.mediaPlayer!!.isPlaying) {
                player?.pause()
                playButton.background =
                    activity?.let { ContextCompat.getDrawable(it, R.drawable.play_icon) };
            }
            else {
                player?.play()
                playButton.background =
                    activity?.let { ContextCompat.getDrawable(it, R.drawable.pause_icon) };
            }
        }
    }
    private fun nextStation(){
        var index = vm.radioStations.indexOf(currentRadioStation)
        currentRadioStation = if(index!=vm.radioStations.lastIndex){
            vm.radioStations[index+1]
        }
        else{
            vm.radioStations.first()
        }
        player?.playMusic(currentRadioStation!!,::showMusic)
    }
    private fun previousStation(){
        var index = vm.radioStations.indexOf(currentRadioStation)
        currentRadioStation = if(index!=0){
            vm.radioStations[index-1]
        }
        else{
            vm.radioStations.last()
        }
        player?.playMusic(currentRadioStation!!,::showMusic)
    }
    private fun showMusic(){
        var song =  if(player?.getMeta()?.title=="")
            "Музыка"
        else player?.getMeta()?.title
        binding?.apply{
            radioPlayer.visibility   = View.VISIBLE
            radioCurrent.text = currentRadioStation?.radioName
            songTitle.text = song
            songTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
            songTitle.isSelected = true
            songTitle.isSingleLine = true
            radioCurrent.isSingleLine = true
            previousButton.setOnClickListener{
                previousStation()
            }
            nextButtom.setOnClickListener{
                nextStation()
            }
            playButton.setOnClickListener{
                onPlayButton()
            }
        }
        vm.currentRadioStation = currentRadioStation
    }
}
