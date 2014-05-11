(ns send-emails.core
  (:require [postal.core :as postal]
            [clojure.string :as string]))

(def email-addresses-filename "email-addresses.txt")
(def from-email "michael.k.baxter@gmail.com")
(def subject "Quick question?")
(def body "Hi,

What is the most important activity you do in your business, and do you have any pain associated with that activity?

I am asking because I am a long-time software developer and am researching how to help make vet practices easier, faster, more fun and profitable. Based on your answer, we could possibly build a solution to help take some of the pain out of your business.

Thank you very much,

Michael Baxter")

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user from-email
           :pass ""})

(defn send-mail
  [password email-addr]
  (postal/send-message (assoc conn :pass password)
                       {:from from-email
                        :to email-addr
                        :subject subject
                        :body body}))

(defn read-email-list-file
  "Reads the list of email address from file defined in email-addresses-filename
   def and returns a list of those addresses"
  []
  (string/split (slurp email-addresses-filename) #"\n"))

(defn send-mails
  "Sends an email given the gmail password of the sender. If no list of emails is given
   as the second parameter, then will send the emails from file email-addresses.txt in the
   base directory."
  ([password]
     (send-mails password (read-email-list-file)))
  ([password emails]
    (for [email emails]
       (do
         (send-mail password email)
         (println "Sent email to" email)))))
