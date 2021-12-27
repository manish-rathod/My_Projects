import { Posts, Posts_model } from "../models/posts_model";
import {
  Errors,
  FileParam,
  FormParam,
  GET,
  PATCH,
  Path,
  POST,
  QueryParam,
} from "typescript-rest";
import { getUser, Verify } from "../decorators/security";
import { User } from "../models/user_model";
import { Likes, Likes_Model } from "../models/likes_model";
import { Images, Image_Model } from "../models/images_model";
const fs = require("fs");

@Path("/post")
@Verify()
export class PostsController {
  @POST
  async createPost(
    body1: any,
    @FileParam("myImage") image: Express.Multer.File
  ) {
    console.log("body " + body1.body);
    var body: Posts = JSON.parse(body1.body);
    console.log("image " + JSON.stringify(image));
    console.log("body " + body.access);
    if (body.access == "private" || body.access == "public") {
      const user: User = getUser();
      console.log("user in create post " + user);

      body.userId = user._id;
      body.userName = user.userName;
      body.likes = 0;
      var imageId = null;
      if (image) {
        console.log("found image in request");
        var imageStr = this.base64_encode(image);
        console.log(imageStr);
        var imageBody: Images = {
          imageString: imageStr,
        };
        var imageModel = new Image_Model(imageBody);
        var imageRes = await imageModel.save();
        imageId = imageRes._id;
        console.log("image " + image.filename);
      }
      if (imageId) {
        body.imageId = imageId;
      }
      var model = new Posts_model(body);
      var res = await model.save();
      // console.log("res model.save " + res);

      var likes: Likes = {
        postId: res._id,
        likes: 0,
        likedByUsers: [],
      };

      var likes_model = new Likes_Model(likes);
      likes_model.save();

      return true;
    } else {
      throw new Errors.NotAcceptableError("Invalid Access type");
    }
  }

  @GET
  async getPosts(): Promise<Posts[]> {
    var model: Posts[] = await Posts_model.find({
      access: "public",
    })
      .sort({ createdAt: -1 })
      .lean();
    if (true) {
      var x = model.map(async (v, i) => {
        var likes: Likes;
        console.log("post id " + model[i]._id);
        likes = await Likes_Model.findOne({
          postId: model[i]._id,
        });

        if (likes) {
          console.log("likes inside postsCOntoller " + likes);
          model[i].likes = likes.likes;
          if (
            likes.likedByUsers &&
            likes.likedByUsers.includes(getUser().userId)
          ) {
            model[i].likedByMe = true;
          } else {
            model[i].likedByMe = false;
          }
        } else {
          model[i].likes = 0;
          model[i].likedByMe = false;
        }
      });
      await Promise.all(x);
      console.log("user in get post " + getUser().userName);
      return model;
    } else {
      for (var i in model) {
        var likes: Likes;
        console.log("post id " + model[i]._id);
        likes = await Likes_Model.findOne({
          postId: model[i]._id,
        });

        if (likes) {
          console.log("likes inside postsCOntoller " + likes);
          model[i].likes = likes.likes;
          if (
            likes.likedByUsers &&
            likes.likedByUsers.includes(getUser().userId)
          ) {
            model[i].likedByMe = true;
          } else {
            model[i].likedByMe = false;
          }
        } else {
          model[i].likes = 0;
          model[i].likedByMe = false;
        }
      }
      console.log("user in get post " + getUser().userName);
      return model;
    }
  }

  base64_encode(file: Express.Multer.File): string {
    var imgBuffer = Buffer.from(file.buffer).toString("base64");
    return "data:" + file.mimetype.toString() + ";base64," + imgBuffer;
  }
}
