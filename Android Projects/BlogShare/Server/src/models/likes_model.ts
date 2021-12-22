import { model, Schema } from "mongoose";

export interface Likes {
  _id?: string;
  postId: string;
  likedByUsers: string[];
  likes: number;
  likedByMe?: boolean;
}

const schema = new Schema<Likes>({
  postId: {
    type: String,
    required: true,
    unique: true,
  },
  likedByUsers: {
    type: [String],
  },
  likes: {
    type: Number,
    required: true,
  },
});

export const Likes_Model = model<Likes>("likes", schema);
// console.log(Likes_Model);
