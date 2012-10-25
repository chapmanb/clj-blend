(ns blend.galaxy.users
  "Retrieve information on Galaxy users.")

(defn get-user-info
  "Retrieve user information for current API client."
  [client]
  (letfn [(user-to-map [user]
            {:username (.getUsername user)
             :email (.getEmail user)})]
    (-> client
        .getUsersClient
        (.showUser "current")
        user-to-map)))