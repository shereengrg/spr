package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

    @PostMapping
    public Speaker create(@RequestBody Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
         speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        if (speaker.getSpeaker_id()!=null & speaker.getCompany()!=null & speaker.getFirst_name()!=null
             & speaker.getLast_name()!=null & speaker.getSessions()!=null & speaker.getSpeaker_bio()!=null
             & speaker.getSpeaker_photo()!=null & speaker.getTitle()!=null) {
            Speaker currentSpeaker = speakerRepository.getOne(id);
            BeanUtils.copyProperties(speaker, currentSpeaker, "speaker_id");
            return speakerRepository.saveAndFlush(currentSpeaker);
        }
        else{
            return new Speaker(HttpStatus.BAD_REQUEST);
        }
    }
}
