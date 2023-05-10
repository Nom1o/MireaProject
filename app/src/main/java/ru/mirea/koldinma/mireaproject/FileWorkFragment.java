package ru.mirea.koldinma.mireaproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.mirea.koldinma.mireaproject.databinding.FragmentFileWorkBinding;
import ru.mirea.koldinma.mireaproject.databinding.FragmentProfileBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileWorkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String fileName = "default.txt";
    FragmentFileWorkBinding binding;

    public FileWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FileWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FileWorkFragment newInstance(String param1, String param2) {
        FileWorkFragment fragment = new FileWorkFragment();
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
        binding = FragmentFileWorkBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.textViewFileData.setText(getTextFromFile());
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fileName.getText() != null) {
                    fileName = binding.fileName.getText().toString() + ".txt";
                    WriteFile(binding.editFileText.getText().toString());
                    binding.textViewFileData.setText(getTextFromFile());
                }
            }
        });
        binding.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fileName.getText() != null) {
                    fileName = binding.fileName.getText().toString() + ".txt";
                    binding.editFileText.setText(getTextFromFile());
                    binding.textViewFileData.setText(getTextFromFile());
                }
            }
        });

        return view;
    }

    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = getActivity().openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);

            return text;
        } catch (IOException ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
    public void WriteFile(String data)
    {
        FileOutputStream outputStream;
        try {
            outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}