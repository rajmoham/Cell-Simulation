
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * A graphical view of the simulation grid. The view displays a rectangle for each location. Each location can contain a cell type which has a colour linked to it respectively.
 * The view also has buttons, sliders labels and a progress bar. The buttons can reset the simulation, pause and resume the simulation, bring up a window showing what the colours mean and
 * basic rulsets and starting the simulation. The progress bar shows the percentage of mycoplasma that is diseased.
 *
 * @author Sam Thomas (K21065562), Raj Mohammad (k20126462) David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2023.02.28
 */

public class SimulatorView extends JFrame {

    private Thread thread;

    // Extends the multi-line plain text view to be suitable for a single-line
    // editor view. (part of Swing)
    private FieldView fieldView;

    // A statistics object computing and storing simulation information
    private FieldStats stats;

    // Text for generation GUI label
    private final String GENERATION_PREFIX = "Generation: ";

    // Text for population GUI label
    private final String POPULATION_PREFIX = "Population: ";

    // GUI labels
    private JLabel genLabel, population, infoLabel;
    JLabel speedLabel = new JLabel("Speed");
    JLabel diseaseLabel = new JLabel("Infection Level");

    // GUI buttons
    private JButton resetButton = new JButton("RESET");
    JButton pauseButton = new JButton("PAUSE");
    JButton startButton = new JButton("Start");
    JButton iconButton = new JButton("ICONS");
    JButton infoButton = new JButton("INFO");

    //GUI BAR AND SLIDER
    private JProgressBar disease = new JProgressBar(0,100);
    private JSlider delaySlider = new JSlider(0,100,50);

    // PANELS
    JPanel infoPane = new JPanel(new BorderLayout());
    JPanel populationPane = new JPanel(new GridBagLayout());


    /**
     * Create a view of the given width and height, adds all buttons, sliders, labels and progress bar to it.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width, Simulator simulator) {
        // Colors used for empty locations.
        genLabel = new JLabel(GENERATION_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        fieldView = new FieldView(height, width);
        stats = new FieldStats();


        stats = new FieldStats();
        setTitle("Life Simulation");
        genLabel = new JLabel(GENERATION_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        fieldView = new FieldView(height, width);

        setLocation(100, 50);

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3,3,3,3);

        infoPane.add(genLabel, BorderLayout.WEST);
        infoPane.add(infoLabel, BorderLayout.CENTER);



        //RESET BUTTON SECTION
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        resetButton.setEnabled(false);
        populationPane.add(resetButton, gbc);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.stop();
                simulator.reset();
                pauseButton.setEnabled(false);
                pauseButton.setText("PAUSE");
                startButton.setEnabled(true);

            }
        });

        // PAUSE BUTTON SECTION
        gbc.gridx = 0;
        gbc.gridy = 2;
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!simulator.getPaused()) {
                        simulator.setPaused(true);
                        resetButton.setEnabled(true);
                        pauseButton.setText("RESUME");
                    }
                    else {
                        simulator.setPaused(false);
                        resetButton.setEnabled(false);
                        pauseButton.setText("PAUSE");
                    }
                }
            });
        populationPane.add(pauseButton, gbc);

        // POPULATION STATISTICS SECTION
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        populationPane.add(population, gbc);

        // ICON BUTTON SECTION
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        iconButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IconGUI iconGUI = new IconGUI();
                }
            });
        populationPane.add(iconButton, gbc);

        // INFO BUTTON SECTION
        gbc.gridx = 4;
        gbc.gridy = 3;
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoGUI infoGUI = new InfoGUI();
            }
        });
        populationPane.add(infoButton, gbc);

        // DISEASE PROGRESS BAR SECTION
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        disease.setStringPainted(true);
        disease.setBackground(Colours.MYCOPLASMA.getColor());
        disease.setForeground(Colours.MYCOPLASMA_DISEASED.getColor());
        disease.setValue(0);
        populationPane.add(disease, gbc);

        // LABEL FOR DISASE PROGRESS BAR
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        populationPane.add(diseaseLabel, gbc);

        // DELAY SLIDER SECTION
        gbc.gridx = 1;
        gbc.gridy = 4;
        delaySlider.setPaintTicks(true);
        delaySlider.setMinorTickSpacing(10);
        populationPane.add(delaySlider, gbc);

        // LABEL FOR SPEED SLIDER
        gbc.gridx = 2;
        gbc.gridy = 3;
        populationPane.add(speedLabel, gbc);


        // START BUTTON SECTION
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Runnable simulate = () -> {
            simulator.simulate(500);
            resetButton.setEnabled(true);
            pauseButton.setEnabled(false);
            pauseButton.setText("PAUSE");

        };
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                simulator.setPaused(false);
                pauseButton.setEnabled(true);
                resetButton.setEnabled(false);
                thread = new Thread(simulate);
                thread.start();
            }
        });
        populationPane.add(startButton, gbc);


        contents.add(infoPane, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(populationPane, BorderLayout.SOUTH);
        setBackground(Colours.BACKGROUND_COLOUR.getColor());
        pack();
        setVisible(true);
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    /**
     * Returns the inverse of the delay sliders value to set the delay as in the simulator class.
     * @return 100 - the value of the delay slider
     */
    public int getDelay() {
        return 100 - delaySlider.getValue();
    }

    /**
     * Show the current status of the field. Including counts of all cell types and a count of how many are diseased.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int generation, Field field) {
        if (!isVisible()) {
            setVisible(true);
        }

        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();
        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);

                if (cell != null && cell.isAlive()) {
                    stats.incrementCount(cell.getClass());
                    if (cell.getDiseased()) {
                        stats.incrementDiseased(cell.getClass());
                    }
                    fieldView.drawMark(col, row, cell.getColor());
                }
                else {
                    fieldView.drawMark(col, row, Colours.BACKGROUND_COLOUR.getColor());
                }
            }
        }

        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

     /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 6;
        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }

    /**
     * Sets the value of the disease progress bar to show the percentage of Mycoplasma that is diseased within the simulation
     */
    public void updateProgressBar() {
        disease.setValue(stats.getDiseasedPercentage());
    }
}
