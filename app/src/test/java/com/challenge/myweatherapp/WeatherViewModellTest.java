package com.challenge.myweatherapp;

import com.challenge.myweatherapp.common.SchedulerProvider;
import com.challenge.myweatherapp.model.City;
import com.challenge.myweatherapp.model.Clouds;
import com.challenge.myweatherapp.model.Coord;
import com.challenge.myweatherapp.model.Main;
import com.challenge.myweatherapp.model.Rain;
import com.challenge.myweatherapp.model.Sys;
import com.challenge.myweatherapp.model.Weather;
import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.model.Wind;
import com.challenge.myweatherapp.service.DataSource;
import com.challenge.myweatherapp.view.weather_screen.WeatherViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WeatherViewModellTest {

    private static Scheduler immediate;
    private WeatherViewModel weatherViewModel;
    private WeatherResult weatherResult;
    private WeatherResponse weatherResponse;
    
    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<WeatherResponse> weatherResponseObserver;

    @Mock
    private Observer<WeatherResult> weatherResultObserver;

    @Mock
    private DataSource dataSource;

    @Mock
    private SchedulerProvider schedulerProvider;

    @BeforeClass
    public static void setUpRxSchedulers() {
        immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setUpWeatherViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        weatherViewModel = new WeatherViewModel(dataSource, schedulerProvider);

        when(schedulerProvider.computation()).thenReturn(WeatherViewModellTest.immediate);
        when(schedulerProvider.io()).thenReturn(WeatherViewModellTest.immediate);
        when(schedulerProvider.ui()).thenReturn(WeatherViewModellTest.immediate);

        weatherResult = getWeatherResult();
        weatherResponse = getWeatherResponse();
    }

    @Test
    public void testWeatherForecasts() {
        // given
        when(dataSource.getForecastsForLocation(anyFloat(), anyFloat())).thenReturn(Single.just(weatherResponse));

        // when
        weatherViewModel.getWeatherForecastsMutableLiveData().observeForever(weatherResponseObserver);
        weatherViewModel.getForecastsForLocation();

        // then
        verify(weatherResponseObserver).onChanged(weatherResponse);
    }

    @Test
    public void testCurrentWeatherResult() {
        // given
        when(dataSource.getCurrentWeatherForLocation(anyDouble(), anyDouble())).thenReturn(Single.just(weatherResult));

        // when
        weatherViewModel.getCurrentWeatherMutableLiveData().observeForever(weatherResultObserver);
        weatherViewModel.getWeatherForCurrentLocation();

        // then
        verify(weatherResultObserver).onChanged(weatherResult);

    }

    static WeatherResponse getWeatherResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCnt(2);
        weatherResponse.setMessage(1.11);
        City city = new City();
        city.setCountry("UK");
        city.setId(2);
        city.setName("London");
        Coord coord = new Coord();
        coord.setLat(2.11);
        coord.setLon(3.33);
        city.setCoord(coord);
        weatherResponse.setCity(city);
        WeatherResult weatherResult = getWeatherResult();
        weatherResponse.setList(Collections.singletonList(weatherResult));
        return weatherResponse;
    }

    static WeatherResult getWeatherResult() {
        WeatherResult weatherResult = new WeatherResult();
        Main main = new Main();
        main.setTemp(11.33);
        weatherResult.setMain(main);
        weatherResult.setDt(12345678);
        Clouds clouds = new Clouds();
        clouds.setAll(2);
        weatherResult.setClouds(clouds);
        Wind wind = new Wind();
        wind.setDeg(22.33);
        wind.setSpeed(44.33);
        weatherResult.setWind(wind);
        weatherResult.setDtTxt("Sunday 02 Mar 2019");
        Sys sys = new Sys();
        sys.setPod("sys_pod");
        weatherResult.setSys(sys);
        Rain rain = new Rain();
        rain.set3h(33.33);
        weatherResult.setRain(rain);
        Weather weather = getWeather();
        weatherResult.setWeather(Collections.singletonList(weather));

        return weatherResult;
    }

    static Weather getWeather() {
        Weather weather = new Weather();
        weather.setDescription("description");
        weather.setIcon("weather_icon");
        weather.setId(222);
        weather.setMain("main");

        return weather;
    }
}