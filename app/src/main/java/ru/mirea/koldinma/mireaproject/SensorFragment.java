package ru.mirea.koldinma.mireaproject;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import ru.mirea.koldinma.mireaproject.databinding.FragmentSensorBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Timer timer;

    private SensorManager sensorManager;
    private Sensor humiditySensor;
    FragmentSensorBinding binding;
    float humidity;

    public SensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorFragment newInstance(String param1, String param2) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        timer.cancel();
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, humiditySensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.resultView.setText("Влажность в помещении = " + Float.toString(humidity) + "%");
                        if (binding.bathButton.isChecked()) {
                            if (humidity >= 40 && humidity <= 60) {
                                binding.textView6.setText("Уровень влажности находится в пределах нормы");
                                binding.resultView.setTextColor(Color.GREEN);
                            } else {
                                binding.textView6.setText("Норма для влажности в этом помещении 40-60%");
                                binding.resultView.setTextColor(Color.RED);
                            }
                        }
                        if (binding.livingButton.isChecked()) {
                            if (humidity >= 40 && humidity <= 60) {
                                binding.textView6.setText("Уровень влажности находится в пределах нормы");
                                binding.resultView.setTextColor(Color.GREEN);
                            } else {
                                binding.textView6.setText("Норма для влажности в этом помещении 40-60%");
                                binding.resultView.setTextColor(Color.RED);
                            }
                        }
                        if (binding.badButton.isChecked()) {
                            if (humidity >= 40 && humidity <= 50) {

                                binding.textView6.setText("Уровень влажности находится в пределах нормы");
                                binding.resultView.setTextColor(Color.GREEN);
                            } else {
                                binding.textView6.setText("Норма для влажности в этом помещении 40-50%");
                                binding.resultView.setTextColor(Color.RED);
                            }
                        }
                        if (binding.roomButton.isChecked()) {
                            if (humidity >= 30 && humidity <= 40) {
                                binding.textView6.setText("Уровень влажности находится в пределах нормы");
                                binding.resultView.setTextColor(Color.GREEN);
                            } else {
                                binding.textView6.setText("Норма для влажности в этом помещении 30-40%");
                                binding.resultView.setTextColor(Color.RED);
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 400);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidity = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}