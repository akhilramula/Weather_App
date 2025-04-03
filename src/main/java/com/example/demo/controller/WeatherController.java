package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.WeatherResponse;

@Controller
public class WeatherController {

	@Value("${api.key}")
	private String apiKey;

	@GetMapping("/")
	public String getIndex() {
		return "index";
	}

	@GetMapping("/getWeather")
	public String getWeather(@RequestParam("city") String city, Model model) {
	    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

	        if (weatherResponse != null) {
	            model.addAttribute("city", weatherResponse.getName());
	            model.addAttribute("country", weatherResponse.getSys().getCountry());
	            model.addAttribute("temperature", weatherResponse.getMain().getTemp());
	            model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
	            model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
	            model.addAttribute("response", weatherResponse.getWeather().get(0).getDescription());
	        }
	    } catch (Exception e) {
	        model.addAttribute("error", "City Not Found. Please enter a valid city name.");
	    }

	    return "index";
	}

}
