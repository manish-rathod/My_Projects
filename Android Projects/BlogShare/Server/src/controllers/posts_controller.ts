import { Posts, Posts_model } from "../models/posts_model";
import { Errors, GET, PATCH, Path, POST, QueryParam } from "typescript-rest";
import { getUser, Verify } from "../decorators/security";
import { User } from "../models/user_model";
import { Likes, Likes_Model } from "../models/likes_model";

@Path("/post")
@Verify()
export class PostsController {
  @POST
  async createPost(body: Posts) {
    if (body.access == "private" || body.access == "public") {
      const user: User = getUser();
      console.log("user in create post " + user);

      body.userId = user._id;
      body.userName = user.userName;
      body.likes = 0;
      var model = new Posts_model(body);
      var res = await model.save();
      console.log("res model.save " + res);

      var likes: Likes = {
        postId: res._id,
        likes: 0,
        likedByUsers: null,
      };

      var likes_model = new Likes_Model(likes);
      likes_model.save();

      return true;
    } else {
      throw new Errors.NotAcceptableError("Invalid Access type");
    }
  }

  @GET
  async getPosts() {
    var model: Posts[] = await Posts_model.find({
      access: "public",
    })
      .sort({ createdAt: -1 })
      .lean();

    var likes: Likes;
    for (var i in model) {
      console.log("post id " + model[i]._id);
      likes = await Likes_Model.findOne({
        postId: model[i]._id,
      });

      if (likes) {
        console.log("likes inside postsCOntoller " + likes);
        model[i].likes = likes.likes;
        if (likes.likedByUsers.includes(getUser().userId)) {
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
