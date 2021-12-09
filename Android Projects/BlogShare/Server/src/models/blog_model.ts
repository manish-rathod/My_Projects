import { Schema } from "mongoose";

export interface Blog {
  header: string;
  content: string;
  access: string;
  userId: string;
}

const schema = new Schema<Blog>({
  userId: {
    type: String,
    required: true,
  },
  header: {
    type: String,
    required: true,
  },
  content: {
    type: String,
    required: true,
  },
  access: {
    type: String,
    required: true,
  },
});
