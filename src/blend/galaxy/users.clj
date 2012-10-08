(ns blend.galaxy.users
  "Retrieve information on Galaxy users.")

(defn get-user-info
  "Retrieve user information for current API client.
   XXX This currently works correctly for non-admin users
   only, and needs an update when current retrieval added to Galaxy."
  [client]
  (letfn [(user-to-map [user]
            {:username (.getUsername user)
             :email (.getEmail user)})]
    (-> client
        .getUsersClient
        .getUsers
        first
        user-to-map)))