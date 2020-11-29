package com.platzi.conf.view.adapter

import com.platzi.conf.model.Speaker

interface SpeakerListener {
    abstract var layoutManager: GridLayoutManager

    fun onSpeakerClicked(speaker: Speaker, position: Int)
}