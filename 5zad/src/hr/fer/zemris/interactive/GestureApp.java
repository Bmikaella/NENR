package hr.fer.zemris.interactive;

import hr.fer.zemris.Util;
import hr.fer.zemris.data.Data;
import hr.fer.zemris.data.Dot;
import hr.fer.zemris.functions.ActivationFunction;
import hr.fer.zemris.functions.ErrorFunction;
import hr.fer.zemris.neuro.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bmihaela.
 */
public class GestureApp extends JFrame {
	private NeuralNetwork neuralNetwork;
	private java.util.List<Data> trainingData = new ArrayList<>();
	private Path trainingDataReadFileLocation;
	private Path trainingDataSaveFileLocation;
	private int iterationsToTrain;
	private int batchSize;
	private double learningRate;
	private int numberOfInputDots;
	private double decayRate;
	private int decayIteration;
	private int[] arhitecture;

	private GestureApp(String fileConfig) {
		configure(fileConfig);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1000, 700);
		setLayout(new BorderLayout());

		initialization();
		intiGUI();
	}

	//iterations
	//batch size
	//learning rate
	// m- number of dots

	//decay rate
	//decay iteration

	// hidenn layers as 3x4x5x2x3
	//save points location
	//previous points if there are some

	private void configure(String fileConfig) {
		List<String> fileData = new ArrayList<>();
		try {
			fileData = Files.readAllLines(Paths.get(fileConfig));
		} catch (IOException e) {
			System.err.println("Config file is not formatted properly");
			System.exit(0);
		}
		if (fileData.size() < 8 || fileData.size() > 9) {
			System.err.println("Config file is not formatted properly");
			System.exit(0);
		}

		iterationsToTrain = Integer.parseInt(fileData.get(0));
		batchSize = Integer.parseInt(fileData.get(1));
		learningRate = Double.parseDouble(fileData.get(2));
		numberOfInputDots = Integer.parseInt(fileData.get(3));
		decayRate = Double.parseDouble(fileData.get(4));
		decayIteration = Integer.parseInt(fileData.get(5));
		List<Integer> arhBuilder = new ArrayList<>();
		arhBuilder.add(numberOfInputDots * 2);
		String[] arhSplit = (fileData.get(6).split("x"));
		for (String a : arhSplit) {
			arhBuilder.add(Integer.parseInt(a));
		}
		arhBuilder.add(5);

		arhitecture = new int[arhBuilder.size()];
		for (int i = 0; i < arhBuilder.size(); i++) {
			arhitecture[i] = arhBuilder.get(i);
		}

		trainingDataSaveFileLocation = Paths.get(fileData.get(7));
		if (fileData.size() > 7) {
			trainingDataReadFileLocation = Paths.get(fileData.get(8));
		}
	}


	private void intiGUI() {
		Canvas canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);

		JPanel option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
		this.add(option, BorderLayout.SOUTH);

		JButton addPointButton = new JButton("Add training example");
		option.add(addPointButton);

		JTextField outputTextField = new JTextField();
		option.add(outputTextField);


		addPointButton.addActionListener(e -> {
			java.util.List<Point> elementData = canvas.getDots();
			if (!elementData.isEmpty()) {
				List<Dot> scaledPoints = Util.preprocess(elementData);
				double[] inputData = Util.getDataForNN(scaledPoints, numberOfInputDots);
				double[] output;
				switch (outputTextField.getText()) {
					case "A":
					case "Alpha":
					case "alpha":
						output = new double[]{1.0, .0, .0, .0, .0};
						break;
					case "B":
					case "Beta":
					case "beta":
						output = new double[]{.0, 1.0, .0, .0, .0};
						break;
					case "G":
					case "Gama":
					case "gama":
						output = new double[]{.0, .0, 1.0, .0, .0};
						break;
					case "D":
					case "Delta":
					case "delta":
						output = new double[]{.0, .0, .0, 1.0, .0};
						break;
					case "E":
					case "Epsilon":
					case "epsilon":
						output = new double[]{.0, .0, .0, .0, 1.0};
						break;
					default:
						output = new double[]{.0, .0, .0, .0, .0};
				}

				Data data = new Data(inputData, output);
				trainingData.add(data);
				canvas.clearDots();
				canvas.repaint();
			}
		});

		JButton trainingNetwork = new JButton("Train network");
		option.add(trainingNetwork);

		trainingNetwork.addActionListener(e -> {
			neuralNetwork.train(trainingData, iterationsToTrain, batchSize, learningRate, true, 500, decayRate,
					decayIteration);
		});

		JButton saveDataToFile = new JButton("Save point data to file");
		option.add(saveDataToFile);

		saveDataToFile.addActionListener(e -> {
			List<String> saveData = trainingData.stream().map(Data::toString).collect(Collectors.toList());
			try {
				Files.write(trainingDataSaveFileLocation, saveData);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});

		JPanel detectionPanel = new JPanel();
		detectionPanel.setLayout(new BoxLayout(detectionPanel, BoxLayout.X_AXIS));

		this.add(detectionPanel, BorderLayout.NORTH);

		JLabel detectionOutput = new JLabel("");
		detectionPanel.add(detectionOutput);
		JButton detectJButton = new JButton("Detect element");
		detectionPanel.add(detectJButton);

		detectJButton.addActionListener(e -> {
			java.util.List<Point> elementData = canvas.getDots();
			String result = "Can't detect anything";
			if (!elementData.isEmpty()) {
				List<Dot> scaledPoints = Util.preprocess(elementData);
				double[] inputData = Util.getDataForNN(scaledPoints, numberOfInputDots);

				int nnresult = neuralNetwork.predictClassification(inputData);

				switch (nnresult) {
					case 0:
						result = "Alpha";
						break;
					case 1:
						result = "Beta";
						break;
					case 2:
						result = "Gama";
						break;
					case 3:
						result = "Delta";
						break;
					case 4:
						result = "Epsilon";
						break;

				}
			}
			detectionOutput.setText(result);

		});
	}

	private void initialization() {
		neuralNetwork = NeuralNetwork.createNetwork(arhitecture, ActivationFunction
				.getSIGMOID(), true, ErrorFunction.getMSE(), 0);
		trainingData = Util.parseNNData(trainingDataReadFileLocation.toString());
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GestureApp(args[0]).setVisible(true));
	}

}
