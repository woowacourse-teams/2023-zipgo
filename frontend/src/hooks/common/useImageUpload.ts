import { ChangeEvent, useState } from 'react';

import { useUploadImageMutation } from '../query/image';

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB;

export const useImageUpload = () => {
  const [previewImage, setPreviewImage] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const { uploadImageMutation } = useUploadImageMutation();

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    const imageFile = e.target.files[0];

    if (!imageFile) return;

    const imageUploadFormData = new FormData();

    if (imageFile.size > MAX_FILE_SIZE) {
      e.target.value = '';
      alert('이미지 크기가 너무 큽니다. 5MB 이하의 이미지를 업로드해주세요.');
      return;
    }

    imageUploadFormData.append('image', imageFile);

    uploadImageMutation.uploadImage({ imageFile: imageUploadFormData }).then(data => {
      setImageUrl(data.imageUrl);
    });

    setPreviewImage(URL.createObjectURL(imageFile));
  };

  const deletePreviewImage = () => {
    URL.revokeObjectURL(previewImage);
    setPreviewImage('');
  };

  return {
    previewImage,
    imageUrl,
    uploadImage,
    deletePreviewImage,
  };
};
