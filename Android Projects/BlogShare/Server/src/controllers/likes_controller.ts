import { getUser, Verify } from "../decorators/security";
import { PATCH, Path, POST, QueryParam } from "typescript-rest";
import { Likes_Model } from "../models/likes_model";

@Path("/like")
@Verify()
export class LikesController {
  @POST
  async likePost(@QueryParam("postId") postId: string) {
    const likedByMe = await Likes_Model.findOne({
      postId: postId,
      likedByUsers: getUser().userId,
    });

    console.log("liked by me " + likedByMe);

    var res;
    if (likedByMe) {
      res = await Likes_Model.findOneAndUpdate(
        {
          postId: postId,
        },
        {
          $inc: { likes: -1 },
          $pull: { likedByUsers: getUser().userId },
        },
        {
          upsert: true,
          new: true,
        }
      ).lean();

      res.likedByMe = false;
    } else {
      res = await Likes_Model.findOneAndUpdate(
        {
          postId: postId,
        },
        {
          $inc: { likes: 1 },
          $push: { likedByUsers: getUser().userId },
        },
        {
          upsert: true,
          new: true,
        }
      ).lean();
      res.likedByMe = true;
    }

    console.log("res " + res);

    return res;
  }
}
