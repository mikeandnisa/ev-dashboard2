import java.awt.*;
import javax.swing.*;

public class InteractiveDashboard3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Main Window Setup
            JFrame frame = new JFrame("SDG EV Dashboard Prototype");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 750);
            frame.setLocationRelativeTo(null);

            // 2. Main Layout Container
            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new GridBagLayout());
            contentPane.setBackground(new Color(245, 245, 245));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10, 10, 10, 10);

            // --- LEFT PANEL (Restored Driving Stats) ---
            JPanel leftPanel = createLeftPanel();
            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25; gbc.weighty = 1.0;
            contentPane.add(leftPanel, gbc);

            // --- CENTER PANEL (Header + Nav + Dynamic Area) ---
            JPanel centerColumn = new JPanel(new BorderLayout(0, 15));
            centerColumn.setBackground(new Color(245, 245, 245));

            // Header Row (Time & Temp)
            JPanel headerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerRow.setBackground(Color.WHITE);
            headerRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            headerRow.add(new JLabel("🕒 12:25 PM    🌡️ 20°C"));
            centerColumn.add(headerRow, BorderLayout.NORTH);

            // Inner Center (Nav + Cards)
            JPanel innerCenter = new JPanel(new BorderLayout(0, 15));
            innerCenter.setBackground(new Color(245, 245, 245));

            // Create a standard panel that will hold one view at a time safely on the web
JPanel centerDynamicArea = new JPanel(new BorderLayout());

            // Navigation Bar
            JPanel navTabs = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            navTabs.setBackground(Color.WHITE);
            JButton navBtn = new JButton("Navigation");
            JButton energyBtn = new JButton("Energy");
            JButton climateBtn = new JButton("Climate");
            JButton mediaBtn = new JButton("Media"); // NEW
            
            navTabs.add(navBtn);
            navTabs.add(energyBtn);
            navTabs.add(climateBtn);
            navTabs.add(mediaBtn);
            
            innerCenter.add(navTabs, BorderLayout.NORTH);
            innerCenter.add(centerDynamicArea, BorderLayout.CENTER); 
            centerColumn.add(innerCenter, BorderLayout.CENTER);

            gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5; gbc.weighty = 1.0;
            contentPane.add(centerColumn, gbc);

            // --- RIGHT PANEL (Systems & Trip Info) ---
            JPanel rightPanel = createRightPanel();
            gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.25; gbc.weighty = 1.0;
            contentPane.add(rightPanel, gbc);

            // --- ACTION LISTENERS (The Navigation Logic) ---
            // --- ACTION LISTENERS (The Navigation Logic) ---
// --- ACTION LISTENERS (Web-Safe Dynamic Swapping) ---
            navBtn.addActionListener(e -> {
                centerDynamicArea.removeAll();
                centerDynamicArea.add(createHomeView(), BorderLayout.CENTER);
                centerDynamicArea.revalidate();
                centerDynamicArea.repaint();
            });

            energyBtn.addActionListener(e -> {
                centerDynamicArea.removeAll();
                centerDynamicArea.add(createEnergyView(), BorderLayout.CENTER);
                centerDynamicArea.revalidate();
                centerDynamicArea.repaint();
            });

            climateBtn.addActionListener(e -> {
                centerDynamicArea.removeAll();
                centerDynamicArea.add(createClimateView(), BorderLayout.CENTER);
                centerDynamicArea.revalidate();
                centerDynamicArea.repaint();
            });

            mediaBtn.addActionListener(e -> {
                centerDynamicArea.removeAll();
                centerDynamicArea.add(createMediaView(), BorderLayout.CENTER);
                centerDynamicArea.revalidate();
                centerDynamicArea.repaint();
            });

            // 3. Force startup on the HOME screen by loading it initially
            centerDynamicArea.add(createHomeView(), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    // ==========================================
    // SIDE PANELS (Left & Right)
    // ==========================================

    private static JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));

        // Speed Box
        JPanel speedBox = new JPanel(new BorderLayout());
        speedBox.setBackground(Color.WHITE);
        speedBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel speedTitle = new JLabel("SPEED", SwingConstants.CENTER);
        speedTitle.setForeground(Color.GRAY);
        JLabel speedVal = new JLabel("79", SwingConstants.CENTER);
        speedVal.setFont(new Font("Arial", Font.BOLD, 60));
        JLabel speedUnit = new JLabel("km/h", SwingConstants.CENTER);
        
        JPanel batteryPanel = new JPanel(new GridLayout(2, 2));
        batteryPanel.setBackground(Color.WHITE);
        batteryPanel.add(new JLabel("Battery")); batteryPanel.add(new JLabel("58%", SwingConstants.RIGHT));
        batteryPanel.add(new JLabel("Range")); batteryPanel.add(new JLabel("287 km", SwingConstants.RIGHT));

        speedBox.add(speedTitle, BorderLayout.NORTH);
        speedBox.add(speedVal, BorderLayout.CENTER);
        
        JPanel southSpeed = new JPanel(new BorderLayout());
        southSpeed.setBackground(Color.WHITE);
        southSpeed.add(speedUnit, BorderLayout.NORTH);
        southSpeed.add(Box.createVerticalStrut(20), BorderLayout.CENTER);
        southSpeed.add(batteryPanel, BorderLayout.SOUTH);
        speedBox.add(southSpeed, BorderLayout.SOUTH);

        // Drive Mode Box
        JPanel modeBox = new JPanel(new GridLayout(2, 2, 10, 10));
        modeBox.setBackground(Color.WHITE);
        modeBox.setBorder(BorderFactory.createTitledBorder("DRIVE MODE"));
        modeBox.add(new JButton("Eco")); modeBox.add(new JButton("Comfort"));
        modeBox.add(new JButton("Sport")); modeBox.add(new JButton("Custom"));

        // Energy Info Box
        JPanel energyBox = new JPanel(new GridLayout(3, 2, 5, 10));
        energyBox.setBackground(Color.WHITE);
        energyBox.setBorder(BorderFactory.createTitledBorder("ENERGY"));
        energyBox.add(new JLabel("⚡ Power Usage")); energyBox.add(new JLabel("54.1 kW", SwingConstants.RIGHT));
        energyBox.add(new JLabel("📈 Efficiency")); energyBox.add(new JLabel("4.0 km/kWh", SwingConstants.RIGHT));
        energyBox.add(new JLabel("🔄 Regen Level")); energyBox.add(new JLabel("Medium", SwingConstants.RIGHT));

        panel.add(speedBox);
        panel.add(Box.createVerticalStrut(15));
        panel.add(modeBox);
        panel.add(Box.createVerticalStrut(15));
        panel.add(energyBox);
        return panel;
    }

    private static JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));

        // Trip Info
        JPanel tripBox = new JPanel(new GridLayout(4, 2, 5, 10));
        tripBox.setBackground(Color.WHITE);
        tripBox.setBorder(BorderFactory.createTitledBorder("TRIP INFO"));
        tripBox.add(new JLabel("Distance")); tripBox.add(new JLabel("167.8 km", SwingConstants.RIGHT));
        tripBox.add(new JLabel("Duration")); tripBox.add(new JLabel("1h 43m", SwingConstants.RIGHT));
        tripBox.add(new JLabel("Avg. Speed")); tripBox.add(new JLabel("73 km/h", SwingConstants.RIGHT));
        tripBox.add(new JLabel("Avg. Consump.")); tripBox.add(new JLabel("5.2 km/kWh", SwingConstants.RIGHT));

        // Systems
        JPanel sysBox = new JPanel(new GridLayout(2, 2, 10, 10));
        sysBox.setBackground(Color.WHITE);
        sysBox.setBorder(BorderFactory.createTitledBorder("SYSTEMS"));
        sysBox.add(createSquareButton("Battery")); sysBox.add(createSquareButton("Motor"));
        sysBox.add(createSquareButton("Brakes")); sysBox.add(createSquareButton("Tire Press."));

        // Quick Settings
        JPanel quickBox = new JPanel(new GridLayout(2, 2, 10, 10));
        quickBox.setBackground(Color.WHITE);
        quickBox.setBorder(BorderFactory.createTitledBorder("QUICK SETTINGS"));
        quickBox.add(createSquareButton("Park Assist")); quickBox.add(createSquareButton("Charging"));
        quickBox.add(createSquareButton("Vent")); quickBox.add(createSquareButton("More"));

        panel.add(tripBox);
        panel.add(Box.createVerticalStrut(15));
        panel.add(sysBox);
        panel.add(Box.createVerticalStrut(15));
        panel.add(quickBox);
        return panel;
    }

    // ==========================================
    // CENTER VIEWS (Home, Energy, Climate, Media)
    // ==========================================

    private static JPanel createHomeView() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel mapArea = new JPanel(new GridBagLayout());
        mapArea.setBackground(new Color(220, 230, 240)); 
        mapArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel mapLabel = new JLabel("📍 Map Navigation Route");
        mapLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mapArea.add(mapLabel);
        
        JPanel tripData = new JPanel(new GridLayout(1, 3, 10, 10));
        tripData.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tripData.add(createStatCard("12:45 PM", "ETA"));
        tripData.add(createStatCard("5.9 km", "Distance"));
        tripData.add(createStatCard("Moderate", "Traffic"));

        panel.add(mapArea, BorderLayout.CENTER);
        panel.add(tripData, BorderLayout.SOUTH);
        return panel;
    }

    private static JPanel createEnergyView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 10, 10));
        statsRow.add(createStatCard("79%", "Battery"));
        statsRow.add(createStatCard("285 km", "Range"));
        statsRow.add(createStatCard("Not Charging", "Status"));
        
        JPanel stationsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        stationsPanel.setBorder(BorderFactory.createTitledBorder("Nearby Charging Stations"));
        stationsPanel.setBackground(Color.WHITE);
        stationsPanel.add(new JLabel("  ⚡ Petronas Kota Samarahan - 1.2 km (1 free)"));
        stationsPanel.add(new JLabel("  ⚡ Petronas Samarahan Exp - 1.7 km (2 free)"));
        stationsPanel.add(new JLabel("  ⚡ Shell Kota Samarahan - 1.2 km (1 free)"));

        panel.add(statsRow);
        panel.add(Box.createVerticalStrut(20)); 
        panel.add(stationsPanel);
        return panel;
    }

    private static JPanel createClimateView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel tempRow = new JPanel(new GridLayout(1, 2, 15, 15));
        tempRow.add(createTempControlCard("DRIVER", "22"));
        tempRow.add(createTempControlCard("PASSENGER", "22"));
        
        JPanel modeRow = new JPanel(new GridLayout(1, 4, 10, 10));
        modeRow.add(createSquareButton("A/C"));
        modeRow.add(createSquareButton("Defrost"));
        modeRow.add(createSquareButton("Heat Seats"));
        modeRow.add(createSquareButton("Auto"));

        panel.add(tempRow);
        panel.add(Box.createVerticalStrut(15));
        panel.add(modeRow);
        return panel;
    }

    // NEW: Media View specific to the latest screenshot
    private static JPanel createMediaView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // 1. Now Playing Banner
        JPanel nowPlaying = new JPanel(new BorderLayout(20, 0));
        nowPlaying.setBackground(new Color(230, 230, 230)); // Mocking the gradient
        nowPlaying.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Mock album art square
        JPanel albumArt = new JPanel();
        albumArt.setBackground(Color.GRAY);
        albumArt.setPreferredSize(new Dimension(100, 100));
        JLabel musicIcon = new JLabel("🎵", SwingConstants.CENTER);
        musicIcon.setFont(new Font("Arial", Font.PLAIN, 40));
        albumArt.add(musicIcon);

        // Song Info
        JPanel songInfo = new JPanel(new GridLayout(3, 1));
        songInfo.setOpaque(false);
        JLabel npLabel = new JLabel("NOW PLAYING");
        npLabel.setForeground(Color.GRAY);
        JLabel titleLabel = new JLabel("In Your Eyes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel artistLabel = new JLabel("The Weeknd");
        artistLabel.setForeground(Color.DARK_GRAY);
        
        songInfo.add(npLabel);
        songInfo.add(titleLabel);
        songInfo.add(artistLabel);

        // Volume Button (Mock)
        JButton volBtn = new JButton("🔊");
        volBtn.setBackground(new Color(50, 150, 250));
        volBtn.setForeground(Color.WHITE);

        nowPlaying.add(albumArt, BorderLayout.WEST);
        nowPlaying.add(songInfo, BorderLayout.CENTER);
        nowPlaying.add(volBtn, BorderLayout.EAST);

        // 2. Media Source Buttons
        JPanel sourceRow = new JPanel(new GridLayout(1, 3, 15, 0));
        sourceRow.add(createSquareButton("Radio"));
        sourceRow.add(createSquareButton("Streaming"));
        sourceRow.add(createSquareButton("Phone"));

        // 3. Presets List
        JPanel presets = new JPanel(new GridLayout(3, 1, 0, 5));
        presets.setBorder(BorderFactory.createTitledBorder("PRESETS"));
        
        JButton catsFmBtn = new JButton("Cats FM");
        catsFmBtn.setHorizontalAlignment(SwingConstants.LEFT);
        JButton sarawakFmBtn = new JButton("Sarawak FM");
        sarawakFmBtn.setHorizontalAlignment(SwingConstants.LEFT);
        JButton hitzFmBtn = new JButton("Hitz FM");
        hitzFmBtn.setHorizontalAlignment(SwingConstants.LEFT);

        presets.add(catsFmBtn);
        presets.add(sarawakFmBtn);
        presets.add(hitzFmBtn);

        panel.add(nowPlaying);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sourceRow);
        panel.add(Box.createVerticalStrut(20));
        panel.add(presets);

        return panel;
    }

    // --- Helper Methods ---

    private static JPanel createTempControlCard(String title, String defaultTemp) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        card.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("  " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        card.add(titleLabel, BorderLayout.NORTH);
        JLabel tempLabel = new JLabel(defaultTemp + "°", SwingConstants.CENTER);
        tempLabel.setFont(new Font("Arial", Font.BOLD, 48));
        card.add(tempLabel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(new JButton("-")); btnPanel.add(new JButton("+"));
        card.add(btnPanel, BorderLayout.SOUTH);
        return card;
    }

    private static JButton createSquareButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(60, 60)); 
        btn.setBackground(Color.WHITE);
        return btn;
    }

    private static JPanel createStatCard(String value, String label) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        card.setBackground(Color.WHITE);
        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel nameLabel = new JLabel(label, SwingConstants.CENTER);
        nameLabel.setForeground(Color.DARK_GRAY);
        card.add(valLabel);
        card.add(nameLabel);
        return card;
    }
}