import { useMutation } from '@tanstack/react-query';

import { postImageFile } from '@/apis/image';

export const useUploadImageMutation = () => {
  const { mutateAsync: uploadImage, ...uploadImageRestMutation } = useMutation({
    mutationFn: postImageFile,
  });

  return { uploadImageMutation: { uploadImage, ...uploadImageRestMutation } };
};
