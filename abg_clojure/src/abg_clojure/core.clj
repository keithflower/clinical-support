(ns abg-clojure.core)

(import '(javax.swing JFrame JLabel JTextField JTextArea JButton)
        '(java.awt.event ActionListener)
        '(java.awt FlowLayout)
        '(quexotic.org Abg))

(defn abg []
  (let [frame (JFrame. "Arterial blood gas interpretation")
        pH-text (JTextField. 3)
        pH-label (JLabel. "pH")
        pCO2-text (JTextField. 3)
        pCO2-label (JLabel. "pCO2")
        HCO3-text (JTextField. 3)
        HCO3-label (JLabel. "HCO3")
        convert-button (JButton. "Interpret")
        no-label (JLabel. "      ")
        abg-label (JTextArea. "ABG Interpretation:")]
    (doto abg-label
      (.setLineWrap true)
      (.setRows 5)
      (.setColumns 10)
      (.setWrapStyleWord true))
    (.addActionListener
     convert-button
     (reify ActionListener 
            (actionPerformed
             [_ evt]
             (let [pH (Double/parseDouble (.getText pH-text))
                   pCO2 (Double/parseDouble (.getText pCO2-text))
                   HCO3 (Double/parseDouble (.getText HCO3-text))]
               (.setText abg-label
                         (Abg/interpret pH pCO2 HCO3))))))
    (doto frame
      (.setLayout (FlowLayout.))
      (.add pH-text)
      (.add pH-label)
      (.add pCO2-text)
      (.add pCO2-label)
      (.add HCO3-text)
      (.add HCO3-label)
      (.add convert-button)
      (.add no-label)
      (.add abg-label)
      (.setSize 600 200)
      (.setVisible true))))
(abg)


