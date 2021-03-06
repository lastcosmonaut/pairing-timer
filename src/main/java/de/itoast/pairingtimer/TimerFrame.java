package de.itoast.pairingtimer;

import de.itoast.pairingtimer.menus.MaxPairingSessionsBeforePauseMenuItem;
import de.itoast.pairingtimer.menus.MaxPairingSessionsMenuItem;
import de.itoast.pairingtimer.menus.PairingDurationMenuItem;
import de.itoast.pairingtimer.menus.PauseDurationMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerFrame extends JFrame implements ConfiguationChangeListener {
    private final TimerPanel timerPanel;
    private TimerConfiguration timerConfiguration;

    public TimerFrame(final TimerPanel timerPanel, TimerConfiguration timerConfiguration) throws HeadlessException {
        timerConfiguration.setConfiguationChangeListener(this);
        this.timerConfiguration = timerConfiguration;
        JFrame frame = new JFrame("Pairing Timer");
        frame.setMinimumSize(new Dimension(400, 400));
        frame.getSize().setSize(400, 400);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        this.timerPanel = timerPanel;
        this.timerPanel.setSize(400, 400);
        frame.add(this.timerPanel, BorderLayout.CENTER);

        addMenuBarTo(frame);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
    }

    private void addMenuBarTo(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        addSettingsMenuTo(menuBar);
        frame.setJMenuBar(menuBar);
    }

    private void addSettingsMenuTo(JMenuBar menuBar) {
        JMenu settings = new JMenu("Settings");

        final JMenuItem menuItem;
        menuItem = new JMenuItem("Start timer");
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.toggle();
                menuItem.setText(timerPanel.getToggleText());
            }
        };

        menuItem.addActionListener(action);
        menuItem.setEnabled(true);
        settings.add(menuItem);


        addPairingDurationSection(settings);
        addPauseDurationSection(settings);
        addPauseSection(settings);
        addMaxPairingSessionsSection(settings);
        menuBar.add(settings);
    }

    private void addMaxPairingSessionsSection(JMenu settings) {
        settings.add(new JSeparator());
        settings.add(new JLabel("Max Pairing sessions..."));

        ButtonGroup group = new ButtonGroup();

        MaxPairingSessionsMenuItem item = new MaxPairingSessionsMenuItem(timerConfiguration);
        group.add(item);
        settings.add(item);

        for (int i=1;i<=6;i++) {
            item = new MaxPairingSessionsMenuItem(i, timerConfiguration);
            group.add(item);
            settings.add(item);
        }

    }

    private void addPauseSection(JMenu settings) {
        settings.add(new JSeparator());
        settings.add(new JLabel("Pause after..."));

        ButtonGroup group = new ButtonGroup();

        for (int i=1;i<=6;i++) {
            MaxPairingSessionsBeforePauseMenuItem item = new MaxPairingSessionsBeforePauseMenuItem(i, timerConfiguration);
            group.add(item);
            settings.add(item);
        }

    }

    private void addPauseDurationSection(JMenu settings) {
        settings.add(new JSeparator());
        settings.add(new JLabel("Pause duration"));

        ButtonGroup group = new ButtonGroup();

        for (int i = 1; i <= 5; i++) {
            PauseDurationMenuItem item = new PauseDurationMenuItem(i * 5, timerConfiguration);
            settings.add(item);
            group.add(item);
        }
    }

    private void addPairingDurationSection(JMenu settings) {
        settings.add(new JSeparator());
        settings.add(new JLabel("Pairing duration"));

        int[] minutes = {10, 15, 20, 25};


        ButtonGroup group = new ButtonGroup();

        for (int minute : minutes) {
            PairingDurationMenuItem menuItem = new PairingDurationMenuItem(minute, timerConfiguration);
            settings.add(menuItem);
            group.add(menuItem);
        }
    }

    @Override
    public void notifyAboutConfigurationChange() {
        timerPanel.restart();
    }
}
