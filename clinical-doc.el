;;
;; Elisp functions in support of clinical documentation
;;
;; by Keith Flower
;;

(defun insert-time ()
  (interactive)
  (insert (format-time-string "%Y-%m-%d-%R")))

(defun journal () 
  (interactive) 
  (find-file "~/journal")
  (end-of-buffer)
  (insert "\n\n")
  (insert "*")
  (insert-time)
  (insert "\n\n")
)

(defun icda (pattern)
  (find-file "~/practice/icda")
  (interactive "sCode? ")
  (occur pattern))

(defun cpt (pattern)
  (find-file "~/practice/cpt")
  (interactive "sCode? ")
  (occur pattern))

(defun note ()
   (interactive)
   (end-of-buffer)
   (insert (car (last (split-string (pwd) "/") 2)))
   (insert "\n")
   (insert-time)
   (insert "\n\n\n")
   (insert "-----------------------------------------------------------------------\n")
   (insert "(Electronically signed with PGP, time/date stamped via electronic mail)\n")
   (previous-line)
   (previous-line)
   (previous-line)
)

(defun sign ()
   (interactive)
   (end-of-buffer)
   (insert "(Electronically signed with GPG, time/date stamped via electronic mail)\n")
)

(defun pop-frame (fn w h x y)
  "Create a new frame with contents of file fn at position x, y and size w, h and contents of file fn"
  (find-file-other-frame fn)
  (let ((frame (selected-frame)))
    ;;(delete-other-windows)
    (set-frame-position frame x y)
    (set-frame-size frame w h)))

(defun ref (fn)
  (interactive "sRef? ")
  "Create a new frame with contents of file fn at position x, y and size w, h and contents of file fn"
  ;; Can get rid of new frame with C-x 5 0
  (find-file-other-frame fn)
  (let ((frame (selected-frame)))
    ;;(delete-other-windows)
    (set-frame-position frame 100 100)
    (set-frame-size frame 60 10)))

(setq psych-mode-keywords
 '(("History of present illness:" . font-lock-function-name-face)
   ("Identification:" . font-lock-function-name-face)
   ("Chief complaint:" . font-lock-function-name-face)
   ("Psychiatric history:" . font-lock-function-name-face)
   ("Psychiatric hospitalizations:" . font-lock-function-name-face)
   ("Psychiatric hospitalizations:" . font-lock-function-name-face)
   ("History of psychiatric medication use:" . font-lock-function-name-face)
   ("History of harm to self:" . font-lock-function-name-face)
   ("History of harm to others:" . font-lock-function-name-face)
   ("Medical history:" . font-lock-function-name-face)
   ("Current medications:" . font-lock-function-name-face)
   ("Drugs/ETOH:" . font-lock-function-name-face)
   ("Family history:" . font-lock-function-name-face)
   ("Social history:" . font-lock-function-name-face)
   ("Mental status exam:" . font-lock-function-name-face)
   ("Diagnostic impressions:" . font-lock-function-name-face)
   ("Treatment plan:" . font-lock-function-name-face)
   ("Allergies:" . font-lock-warning-face)))

(define-derived-mode psych-mode text-mode 
  "psych" 
  (set (make-local-variable 'font-lock-defaults) 
       '(psych-mode-keywords t))) 

(defun pnote ()
   (interactive)
   (yas/expand-or-visit-from-menu (quote text-mode) "ptnote")  
)

(defun update-meds ()
  "Write the contents of a 'medication table' in a buffer to a file"
  (interactive)
   (save-excursion
     (beginning-of-buffer)
     (search-forward "| Medication") ;; a bit brittle if we change the table headers, obviously
     (beginning-of-line)
     (let ((beg (point))) (goto-char (org-table-end)) (write-region beg (point) "meds.org"))))

(setq frame-title-format
      (list 
        '(buffer-file-name "%f" (dired-directory dired-directory "%b"))))

(defun esign ()
  "Invoke shell test"
  (interactive)
  ;; (interactive "MName of shell buffer to create: ")
  (let ((efilename (file-name-nondirectory (buffer-file-name (current-buffer-not-mini)))))
    (cd (file-name-directory (buffer-file-name (current-buffer-not-mini))))
     (pop-to-buffer (get-buffer-create (generate-new-buffer-name "fred")))
     (shell (current-buffer))
     (process-send-string nil (format "ls %s\n" efilename))
     (process-send-string nil (format "esign %s \n" efilename))))

(defun current-buffer-not-mini ()
  "Return current-buffer if current buffer is not the *mini-buffer*
  else return buffer before minibuf is activated."
  (if (not (window-minibuffer-p)) (current-buffer)
      (if (eq (get-lru-window) (next-window))
    	  (window-buffer (previous-window)) (window-buffer (next-window)))))

;; Org mode stuff
(setq load-path (cons "~/.emacs.d/org-latest/org-mode/lisp" load-path))
(setq load-path (cons "~/.emacs.d/org-latest/org-mode/contrib/lisp" load-path))
;;(set-face-attribute 'org-table nil :inherit 'fixed-pitch)

(require 'org)

(global-set-key "\C-cl" 'org-store-link)
(global-set-key "\C-cc" 'org-capture)
(global-set-key "\C-ca" 'org-agenda)
(global-set-key "\C-cb" 'org-iswitchb)

(setq org-default-notes-file (concat org-directory "~/practice/org/notes.org"))
(setq org-log-done t)

(setq org-agenda-files (list "~/practice/org/psych_practice.org"
			     "~/practice/org/personal.org"))

(setq org-capture-templates
      '(("t" "Personal todo" entry (file+headline "~/practice/org/personal.org" "Tasks")
             "* TODO %?\n  %i\n")
        ("w" "Practice todo" entry (file+headline "~/practice/org/psych_practice.org" "Tasks")
             "* TODO %?\n  %i\n")
        ("p" "Phone log" entry (file+datetree "~/practice/org/phone_log.org")
             "* %?\nEntered on %U\n  %i\n")
        ("j" "Journal" entry (file+datetree "~/org/journal.org")
             "* %?\nEntered on %U\n  %i\n")))

;; Set to the location of your Org files on your local system
(setq org-directory "~/practice/org")
;; Set to the name of the file where new notes will be stored
(setq org-mobile-inbox-for-pull "~/practice/org/flagged.org")
;; Set to <your Dropbox root directory>/MobileOrg.
(setq org-mobile-directory "~/Dropbox/Apps/MobileOrg")
;; Enable encryption
(setq org-mobile-use-encryption t)
;; Set a password
(setq org-mobile-encryption-password "matahari")

(defun pn ()     
  (interactive)     
  (let ((daily-name (format-time-string "%Y%m%dpn")))
      (find-file (concat daily-name ".txt")))
  (sensitive-mode)     
  (pnote))

(defun note-body ()
   (interactive)
   (end-of-buffer)
   (insert (car (last (split-string (pwd) "/") 2)))
   (insert "\n")
   (insert-time)
   (insert "\n\n\n")
   (insert "-----------------------------------------------------------------------\n")
   (insert "(Electronically signed with PGP, time/date stamped via electronic mail)\n")
   (insert "Keith Flower, MD")
   (previous-line)
   (previous-line)
   (previous-line)
)

(defun note ()     
  (interactive)
  (let ((daily-name (format-time-string "%Y%m%dnote")))
      (find-file (concat daily-name ".txt")))
  (sensitive-mode)     
  (note-body))

(defun fuzzy-cd (s d)
  "Change to subdirectory that matches input string s in a given parent directory d"
  (interactive)
  (cd d)
  (let ((result  ()))
      (dolist (word  (directory-files d))
        (when (string-match s word) (push word result)))
      (cd (car (nreverse result)))))

(defun pn-for (whox)
   (interactive "sWho? ")
   (fuzzy-cd whox "~/patients")
   (pn))

(defun note-for (whox)
   (interactive "sWho? ")
   (fuzzy-cd whox "~/patients")
   (note))

(define-minor-mode sensitive-mode
  "For sensitive files where we don't want multiple
copies laying around.
It disables backup creation. You can uncomment
some lines below if you don't want autosave.

With no argument, this command toggles the mode.
Non-null prefix argument turns on the mode.
Null prefix argument turns off the mode."
  ;; The initial value.
  nil
  ;; The indicator for the mode line.
  " Sensitive"
  ;; The minor mode bindings.
  nil
  (if (symbol-value sensitive-mode)
      (progn
	;; disable backups
	(set (make-local-variable 'backup-inhibited) t)	
	;; disable auto-save
	;; (if auto-save-default
	    ;;(auto-save-mode -1)))
    ;resort to default value of backup-inhibited
    (kill-local-variable 'backup-inhibited)
    ;resort to default auto save setting
    (if auto-save-default
	(auto-save-mode 1)))))

