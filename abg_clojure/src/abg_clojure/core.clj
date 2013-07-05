(ns abg-clojure.core)

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(quexotic.org Abg))

(defn celsius []
  (let [frame (JFrame. "Celsius Converter")
        pH-text (JTextField.)
        pH-label (JLabel. "pH")
        pCO2-text (JTextField.)
        pCO2-label (JLabel. "pCO2")
        HCO3-text (JTextField.)
        HCO3-label (JLabel. "HCO3")
        convert-button (JButton. "Interpret")
        fahrenheit-label (JLabel. "ABG Interpretation:")]
    (.addActionListener
     convert-button
     (reify ActionListener 
            (actionPerformed
             [_ evt]
             (let [c (Double/parseDouble (.getText pH-text))]
               (.setText fahrenheit-label
                         (str (+ 32 (* 1.8 c)) " xxxx"))))))
    (doto frame
      (.setLayout (GridLayout. 6 1 3 3))
      (.add pH-text)
      (.add pH-label)
      (.add pCO2-text)
      (.add pCO2-label)
      (.add HCO3-text)
      (.add HCO3-label)
      (.add convert-button)
      (.add fahrenheit-label)
      (.setSize 300 480)
      (.setVisible true))))

(defn foo
  "I don't do a whole lot."
  (Abg/interpret 7.2 24.0 13.0))

(celsius)
(foo)
