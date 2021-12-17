import express from "express";
var httpContext = require("express-http-context");
var bodyParser = require("body-parser");
import { Server } from "typescript-rest";
import services from "./controllers";
import mongoose from "mongoose";
require("dotenv").config();

var server: express.Application = express();
server.use(bodyParser.json()); //must come before
server.use(httpContext.middleware);
server.use((req, res, next) => {
  httpContext.ns.bindEmitter(req);
  httpContext.ns.bindEmitter(res);
  var requestId = req.headers["x-request-id"] || "";
  httpContext.set("requestId", requestId);
  // console.log('request Id set is: ', httpContext.get('requestId'));
  // console.log(`Body in middleware ${JSON.stringify(req.body)}`)
  next();
});

export class ApiServer {
  public PORT: String = process.env.PORT || "5000";
  constructor() {
    Server.buildServices(server, ...services);
  }

  start() {
    server.listen(this.PORT, () => {
      console.log("server is running at port " + this.PORT);
      this.printAllRoutes();
    });

    mongoose.set("debug", true);
    mongoose
      .connect(process.env.DB_URL)
      .then(() => {
        console.log("Connected to mongo db");
      })
      .catch((e) => {
        console.log("Error connecting to fb " + e);
      });
  }

  printAllRoutes() {
    console.log("Routes=>");
    Server.getPaths().forEach((element) => {
      console.log(element);
    });
  }
}
