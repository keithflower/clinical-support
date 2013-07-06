(ns abg-clojure.core)

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(quexotic.org Abg))

(defn celsius []
  (let [frame (JFrame. "Arterial blood gas interpretation")
        pH-text (JTextField.)
        pH-label (JLabel. "pH")
        pCO2-text (JTextField.)
        pCO2-label (JLabel. "pCO2")
        HCO3-text (JTextField.)
        HCO3-label (JLabel. "HCO3")
        convert-button (JButton. "Interpret")
        no-label (JLabel. "")
        fahrenheit-label (JLabel. "ABG Interpretation:")]
    (.addActionListener
     convert-button
     (reify ActionListener 
            (actionPerformed
             [_ evt]
             (let [pH (Double/parseDouble (.getText pH-text))
                   pCO2 (Double/parseDouble (.getText pCO2-text))
                   HCO3 (Double/parseDouble (.getText HCO3-text))]
               (.setText fahrenheit-label
                         (Abg/interpret pH pCO2 HCO3))))))
    (doto frame
      (.setLayout (GridLayout. 6 1 3 3))
      (.add pH-text)
      (.add pH-label)
      (.add pCO2-text)
      (.add pCO2-label)
      (.add HCO3-text)
      (.add HCO3-label)
      (.add convert-button)
      (.add no-label)
      (.add fahrenheit-label)
      (.setSize 300 480)
      (.setVisible true))))
(celsius)


