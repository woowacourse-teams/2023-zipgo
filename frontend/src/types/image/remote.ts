interface PostImageFileReq {
  imageFile: FormData;
}

interface PostImageFileRes {
  imageUrl: string;
}

export type { PostImageFileReq, PostImageFileRes };
