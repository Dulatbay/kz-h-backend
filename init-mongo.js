db = db.getSiblingDB('kzh');

db.createUser({
    user: "user_qit",
    pwd: "123",
    roles: [{ role: "readWrite", db: "kzh" }]
});
