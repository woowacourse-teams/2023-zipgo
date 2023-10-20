import imageCompression from 'browser-image-compression';
import { ChangeEvent, useState } from 'react';

import { PET_PROFILE_IMAGE_COMPRESSION_OPTION } from '@/constants/petProfile';
import { useUploadImageMutation } from '@/hooks/query/image';

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB;

export const useImageUpload = () => {
  const [previewImage, setPreviewImage] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const { uploadImageMutation } = useUploadImageMutation();

  const uploadImage = async (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    const originalImageFile = e.target.files[0];

    if (!originalImageFile) return;

    if (originalImageFile.size > MAX_FILE_SIZE) {
      e.target.value = '';
      alert('이미지 크기가 너무 큽니다. 5MB 이하의 이미지를 업로드해주세요.');

      return;
    }

    const compressedImageBlob = await imageCompression(
      originalImageFile,
      PET_PROFILE_IMAGE_COMPRESSION_OPTION,
    );

    const imageUploadFormData = new FormData();

    imageUploadFormData.append('image', compressedImageBlob);

    uploadImageMutation.uploadImage({ imageFile: imageUploadFormData }).then(data => {
      setImageUrl(data.imageUrl);
    });

    setPreviewImage(URL.createObjectURL(compressedImageBlob));
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
