package com.engineerai.android.model.responsemodel;
 import java.util.List;
public class APIResponse {
    private boolean status;

    private String message;

    private Data data;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private List<Users> users;

        private boolean has_more;

        public void setUsers(List<Users> users) {
            this.users = users;
        }

        public List<Users> getUsers() {
            return this.users;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public boolean getHas_more() {
            return this.has_more;
        }

        public class Users {
            private String name;

            private String image;

            private List<String> items;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImage() {
                return this.image;
            }

            public void setItems(List<String> items) {
                this.items = items;
            }

            public List<String> getItems() {
                return this.items;
            }
        }
    }

}


