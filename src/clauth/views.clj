(ns clauth.views
  (:use ring.util.response)
  (:use [clauth.middleware :only [csrf-token]])
  (:use hiccup.core))

(defn login-form 
  ([] (login-form "/login" nil nil))
  ([req] (login-form req (req :uri) ((req :params) "username") ((req :params) "password")))
  ([req uri username password]
    (html
      [:form {:action uri :method :post}
        [:input {:type "hidden" :name "csrf-token" :value (csrf-token req)}]
        [:label {:for "username"} "User name:"]
        [:input {:type "text" :id "username" :name "username" :value username}]

        [:label {:for "password"} "Password:"]
        [:input {:type "password" :id "password" :name "password" :value password }]

        [:button {:type "submit" :class "btn"} "Login"]])))

(defn login-form-handler
  "Login form ring handler"
  [req]
  {
    :status 200
    :headers {"Content-Type" "text/html"}
    :body (login-form req)})

(defn hello-world
  [req]
  (let [user (:subject (req :access-token))]
    (response (html
              [:h1 "Hello " (:login user)]))))