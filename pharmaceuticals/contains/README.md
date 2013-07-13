Meds/ingredients
===========================

Functions to find drugs containing a given ingredient. For example, to find the list of medications containing acetaminophen:

    cv = contains([first_ranked_cui(name_resolve_json('acetaminophen'))])
    cl = extract_from_json(cv, "name", l=[])
    pp = pprint.PrettyPrinter(indent=5)
    print "{0} medications contain acetaminophen".format(len(cl))
    print "\n".join(cl)


    406 medications contain acetaminophen:

          666 Cold Preparation
          Acephen
          Acetadrink
          Acetadryl
          Actamin
          Actifed Cold & Sinus
          Actifed Plus
          Acuflex
          Adprin B
          Alagesic
          Ali Flex
          Alka-Seltzer Cold and Sinus
          Alka-Seltzer Plus Cold
          Alka-Seltzer Plus Cold Liquigel
          Alka-Seltzer Plus Cold and Sinus
          Alka-Seltzer Plus Cough and Cold Liquigel
          Alka-Seltzer Plus Cough and Cold Liquigel Reformulated Aug 2011
          Alka-Seltzer Plus Day Severe Cold, Cough And Flu
          Alka-Seltzer Plus Flu Liquigels
          Alka-Seltzer Plus Flu Reformulated Jan 2011
          Alka-Seltzer Plus Night Cold and Flu
          Alka-Seltzer Plus Night Severe Cold, Cough and Flu
          Alka-Seltzer Plus Night Time Cold Liquigel
          Alka-Seltzer Plus Night Time Reformulated Dec 2006
          Alka-Seltzer Plus Severe Allergy
          Alka-Seltzer Plus Severe Sinus Congestion and Cough
          Allerest Headache Strength
          Allerest No Drowsiness
          Allerest Sinus
          Alpain
          Anabar
          Anacin AF
          Anacin Advanced Headache Formula
          Anacin PM Aspirin Free
          Anexsia
          Anolor
          Apra
          Arthriten Inflammatory Pain
          BF-Paradac
          BP Poly-650
          Backaid
          Backaid IPF
          Backprin
          Bactimicina
          Baczol Cold Medicine
          Balacet
          Bayer Migraine
          Bayer Select Decongestant
          Benadryl Allergy Cold
          Benadryl Allergy Cold Reformulated Jun 2007
          Benadryl Severe Allergy Sinus Headache Reformulated Jun 2007
          Bromo Seltzer
          Bupap
          By Ache
          Cafgesic
          Capacet
          Capital and Codeine
          Cephadyn
          Cetafen
          Children's Mucinex Multi-Symptom Cold and Fever
          Childrens Tylenol Cold Plus Cough
          Childrens Tylenol Plus Cold & Allergy
          Co-Gesic
          Cocet
          Codrix
          Comtrex Allergy Sinus
          Comtrex Cold and Cough Nighttime
          Comtrex Cold and Cough Non Drowsy
          Comtrex Cold and Flu Maximum Strength Liquid
          Comtrex Cold and Flu Maximum Strength Reformulated Aug 2006
          Comtrex Deep Chest Cold Non Drowsy
          Comtrex Nighttime Acute Head Cold
          Comtrex Non-Drowsy
          Comtrex Sore Throat Relief
          Contac Cold and Flu Cooling Night
          Contac Cold and Flu Maximum Strength
          Contac Cold and Flu Non Drowsy Maximum Strength
          Contac Severe Cold and Flu Non Drowsy
          Contragesic
          Coricidin
          Coricidin D Cold
          Coricidin HBP Flu Maximum Strength
          Coricidin HBP Nighttime Multi-Symptom Cold Reformulated Feb 2013
          Coricidin Night Time Cold Relief
          Cotabflu
          CounterAct Pain
          Counteract Day
          Counteract Night
          Counteract PM
          Darvocet
          DayQuil Sinex
          Dayquil
          Dayquil Cold & Flu
          Dayquil Liquicaps Reformulated Apr 2009
          Dayquil Sinus
          Delsym Adult Night Time Multi-Symptom
          Delsym Children's Nighttime Cough and Cold Reformulated Apr 2013
          Delsym Cough Plus Cold Daytime
          Delsym Cough Plus Cold Night Time
          Delsym Night Time Cough and Cold
          Diabetic Tussin Night Time Formula
          Dilotab
          Dimetapp Nighttime Flu
          Dimetapp Nighttime Flu Reformulated Sep 2007
          Dolacet
          Dolgic LQ
          Dolgic Plus
          Dologen
          Dologesic
          Dolorex Forte
          Dristan Cold
          Dristan Cold Multi Symptom
          Drixoral Sinus
          Durabac
          Durabac Forte
          Duraflu
          Duraxin
          EZ III
          Ed Flex
          Elixsure Fever/Pain
          Emagrin Forte
          Endocet
          Ephed Plus Cold Flu and Sinus
          Epidrin
          Esgic
          Exaprin
          Excedrin
          Excedrin Aspirin Free
          Excedrin Back & Body
          Excedrin PM
          Excedrin Quick Tab
          Excedrin Sinus
          Excedrin Sinus Headache
          Excedrin Tension Headache
          Feverall
          Fioricet
          Fioricet with Codeine
          Flextra
          Flextra Plus
          Frenadol
          Genapap
          Geone
          Goody's Body Pain
          Goody's Extra Strength
          Goody's Headache Relief Shot
          Goody's Migraine Relief
          Goody's PM
          Histenol
          Hy-Phen
          Hycet
          Infantaire
          Kolephrin
          Kolephrin DM
          Lagesic
          Legatrin PM
          Levacet
          Liquicet
          Little Colds
          Little Fevers
          Lorcet
          Lortab
          Lusonex Plus
          Lynox
          Magnacet
          Mapap
          Mapap Cold Formula
          Mapap PM
          Mapap Sinus Congestion and Pain
          Margesic
          Margesic-H
          Marten-Tab
          Maxidone
          Maxiflu CD
          Maxiflu DM
          Medigesic
          Mejoralito
          Midol Maximum Strength Menstrual
          Midol PM
          Midol PM Reformulated Apr 2011
          Midol PMS Maximum Strength
          Midol Teen
          Midrin
          Mucinex Children's Night Time Multi-Symptom Cold
          Mucinex Fast-Max Cold and Sinus
          Mucinex Fast-Max Cold, Flu and Sore Throat
          Mucinex Fast-Max Night Time Cold and Flu
          Mucinex Fast-Max Severe Cold
          Mucinex Sinus-Max Day
          Mucinex Sinus-Max Night
          Mygrex
          Nature Fusion Cold & Flu
          Nonbac
          Norco
          Norel AD
          Norel SR
          Nortemp
          Novagesic
          NyQuil D
          NyQuil Sinex
          Nyquil Alcohol Free
          Nyquil Cold & Flu
          Nyquil Multi-Symptom
          Ofirmev
          Onetab Cold and Flu
          Onset Forte
          Orbivan
          Orbivan CF
          Ornex
          Painaid
          Painaid BRF
          Painaid ESF
          Pamprin Cramp Formula
          Pamprin Max Formula
          Pamprin Multi-Symptom
          Panadol
          Panadol Cold & Flu Non Drowsy
          Panadol PM
          Pancold S
          Panlor DC Reformulated Jan 2008
          Panlor SS
          PediaCare Children's Plus Cough and Sore Throat
          Pediacare Children's Fever Reducer Pain Reliever
          Pediacare Children's Plus Cough and Runny Nose
          Pediacare Infant Fever Reducer
          Percocet
          Percogesic Reformulated Jan 2011
          Pharbetol
          Phenflu CD
          Phenflu DM
          Phrenilin
          Phrenilin with Caffeine and Codeine
          Poly-Vent Plus
          Premsyn PMS
          Primalev
          Prodrin
          Promacet
          Protid
          Pyrroxate Cold & Congestion
          Redutemp
          Relagesic
          Repan
          Respa C&C
          Rhinoflex
          Rhinogesic
          Rid-A-Pain
          Ringl
          Robitussin Cold Cough and Flu
          Robitussin Honey Flu Nighttime
          Robitussin Honey Flu Non-Drowsy
          Robitussin Night Cold
          Robitussin Night Relief
          Robitussin Peak Cold Daytime Cold Plus Flu
          Robitussin Peak Cold Nasal
          Robitussin Peak Cold Nighttime Cold Plus Flu
          Robitussin Peak Cold Nighttime Multi-Symptom Cold
          Robitussin Peak Cold Nighttime Nasal Relief
          Roxicet
          Rx-Act Cold Head Congestion
          Rx-Act Flu & Severe Cold & Cough
          Rx-Act Flu & Sore Throat
          Rx-Act Headache Formula
          Rx-Act Nighttime
          Rx-Act Pain Relief
          Rx-Act Pain Relief PM
          Rx-Act Sinus Congestion & Pain
          Saleto
          SanaTos Night
          Sanatos Day
          Scot-Tussin Multisymptom Cold and Allergy
          Sedalmex
          Sedapap
          Silapap
          Sinarest
          Sinarest Sinus
          Sine-Off Cold and Cough
          Sine-Off Maximum Strength
          Sine-Off Maximum Strength Reformulated Sep 2008
          Sine-Off Sinus and Cold
          Singlet
          Sinutab Ex-Strength
          Sinutab Sinus
          St. Joseph Aspirin-Free
          Stagesic
          Stona
          Sudafed PE Cold & Cough
          Sudafed PE Nighttime Cold
          Sudafed PE Pressure Plus Pain Plus Cough
          Sudafed PE Pressure Plus Pain Plus Mucus
          Sudafed PE Severe Cold
          Sudafed PE Sinus Headache
          Sudafed PE Triple Action
          Sudafed Triple Action
          Supac
          Syncol
          T-Painol
          T-Painol Extra Strength
          Tactinal
          Talacen
          Tavist Allergy/Sinus/Headache
          Tavist Sinus
          Tempra
          Tempra 2
          Tempra Quicklets
          Tencon
          Theraflu Cold & Sore Throat
          Theraflu Cold & Sore Throat Reformulated Sep 2008
          Theraflu Daytime Severe Cold
          Theraflu Daytime Severe Cold & Cough
          Theraflu Flu & Chest Congestion
          Theraflu Flu & Sore Throat
          Theraflu Flu & Sore Throat Reformulated Sep 2008
          Theraflu Flu and Cold Medicine Powder
          Theraflu Max-D
          Theraflu Nighttime Maximum Strength
          Theraflu Nighttime Severe Cold
          Theraflu Nighttime Severe Cold & Cough
          Theraflu Nighttime Severe Cold Capsule
          Theraflu Severe Cold & Congestion Non-Drowsy
          Theraflu Severe Cold Nighttime
          Theraflu Sore Throat Maximum Strength
          Theraflu Warming Cold & Chest Congestion
          Theraflu Warming Relief
          Theraflu, Flu, Cold, and Cough
          Trezix
          Trezix Reformulated Oct 2011
          Triaminic Cold and Fever
          Triaminic Cough & Sore Throat
          Triaminic Cough & Sore Throat Reformulated Jul 2007
          Triaminic Fever & Pain
          Triaminic Infant Drops Reformulated Nov 2010
          Triaminic Multi-Symptom Fever
          Triaminic Softchews Allergy Sinus
          Triaminic Softchews Cough & Sore Throat
          Triaminic Softchews Cough & Sore Throat Reformulated Jul 2007
          Triaminic Sore Throat Formula
          Triaminicin
          Tycolene
          Tylenol
          Tylenol Allergy Multi-Symptom
          Tylenol Allergy Multi-Symptom Nighttime
          Tylenol Allergy Sinus
          Tylenol Chest Congestion
          Tylenol Children's Multi-Symptom Cold Plus
          Tylenol Children's Plus Cold
          Tylenol Children's Plus Cold & Cough
          Tylenol Children's Plus Cold Reformulated Mar 2013
          Tylenol Childrens Plus Cough & Runny Nose
          Tylenol Childrens Plus Cough & Sore Throat
          Tylenol Cold
          Tylenol Cold & Flu Severe Day Time
          Tylenol Cold Complete Formula
          Tylenol Cold Head Congestion Severe
          Tylenol Cold Multi-Symptom Daytime
          Tylenol Cold Multi-Symptom Nighttime
          Tylenol Cold Multi-Symptom Nighttime Liquid
          Tylenol Cold Multi-Symptom Severe Daytime
          Tylenol Cold Relief Nighttime
          Tylenol Cold Severe Congestion Non-Drowsy
          Tylenol Cough & Sore Throat Night Time
          Tylenol Cough and Sore Throat Daytime
          Tylenol Flu Maximum Strength Nighttime
          Tylenol PM
          Tylenol Severe Allergy
          Tylenol Sinus
          Tylenol Sinus Congestion and Pain Daytime
          Tylenol Sinus Congestion and Pain Severe
          Tylenol Sinus NightTime
          Tylenol Sinus Severe Congestion
          Tylenol with Codeine
          Tylox
          Ultracet
          Un-Aspirin
          Uniserts
          Unisom with Pain Relief
          Valorin Extra
          Vanquish
          Vicks 44 Cold, Flu and Cough
          Vicks Formula 44 Custom Care Cough & Cold PM
          Vicks Formula 44M
          Vicks Nature Fusion Cold and Flu Night
          Vicodin
          Vistra
          Vitapap
          Vopac
          Wal-Dryl Severe Allergy & Sinus
          Wal-Flu Cold and Sore Throat
          Wal-Flu Daytime Severe Cold and Cough
          Wal-Flu Flu and Sore Throat
          Wal-Flu Severe Cold
          Wal-Flu Severe Cold and Cough
          Wal-Phed Cold & Cough
          Wal-Phed PE Severe Cold
          Womens Tylenol Menstrual Relief
          XL-DOL
          Xodol
          Xolox
          Yinchiao Fast Relief Flu
          Zamicet
          Zebutal
          Zflex
          Zgesic
          Zicam Flu Nighttime
          Zicam Multi-Symptom Cold and Flu Daytime
          Zicam Multi-Symptom Cold and Flu Nighttime
          Zolvit
          Zydone
