(load "~~/lib/syntax-case")
(load "myenv.scm")
(load "lib/term.scm")
(load "lib/kanren.ss")

;; Assert a few simple facts
(define causes
  (extend-relation (a1 a2)
                   (fact () 'schizophrenia 'paranoia)
                   (fact () 'depression 'paranoia)
                   (fact () 'depression 'anhedonia)
                   (fact () 'depression 'insomnia)))
 
;; We can also add facts to our little ontology
(define causes
  (extend-relation (a1 a2) causes
    (fact () '(bipolar disorder) 'paranoia)))
(define causes
  (extend-relation (a1 a2) causes
    (fact () '(bipolar disorder) 'mania)))
(define causes
  (extend-relation (a1 a2) causes
    (fact () '(bipolar disorder) 'insomnia)))
 
;; Establish relationship of anatomical structures
(define caudal-to
  (extend-relation (a1 a2)
    (fact () 'capsule 'midbrain)
    (fact () 'midbrain 'pons)
    (fact () 'pons 'medulla)
    (fact () 'medulla 'cord)))
 
(define crosses-at
  (extend-relation (a1)
    (fact () 'pons)))
 
(define caudal-all
  (lambda (cephalic caudal)
    (any
     (adjacent cephalic caudal)
     (exists (intermediate)
       (all (adjacent cephalic intermediate)
         (caudal-all intermediate caudal))))))
 
(define ipsilateral-symptoms?
  (lambda (lesion-at)
    (names (solve 5 (x)
      (caudal-all lesion-at
        (car (names
          (solve 5 (s) (crosses-at s))))))))
 
;; Prettify solutions a bit
(define names
  (lambda (ls)
     (map car
          (map cdr
               (map car ls)))))
 
;; Try a simple relationship query:
;;   What symptoms can depression cause?
;;   (solve 5 ...) means 'give me at most 5 solutions
;;   for the logic variable x that would make
;;   (causes 'depression x) true.
> (names (solve 5 (x) (causes 'depression x)))
(paranoia anhedonia insomnia)
 
;; What symptoms can bipolar disorder cause?
> (names (solve 5 (x) (causes '(bipolar disorder) x)))
(paranoia mania insomnia)
 
;; What conditions cause paranoia?
> (names (solve 5 (x) (causes x 'paranoia)))
(schizophrenia depression (bipolar disorder))
 
;; What structure is immediately caudal to the midbrain?
> (names (solve 5 (x) (caudal-to 'midbrain x)))
(pons)
 
;; What are *all* structures caudal to midbrain?
> (names (solve 5 (x) (caudal-all 'midbrain x)))
(pons medulla cord)
 
;; What structure is cephalic to the pons?
;;    (Note reordering of terms in the predicate.
;;    You can read this logic statement as 'give me
;;    all answers such that x makes predicate caudal-all true'
> (names (solve 5 (x) (caudal-to x 'pons)))
(midbrain)
 
;; Give all terms cephalic to pons:
> (names (solve 5 (x) (caudal-all x 'pons)))
(midbrain capsule)
 
;; Are symptoms ipsilateral to a given lesion location?
> (ipsilateral-symptoms? 'cord)
()  ;; i.e., false (empty list)
 
> (ipsilateral-symptoms? 'medulla)
(_.0) ;; i.e., true for all
