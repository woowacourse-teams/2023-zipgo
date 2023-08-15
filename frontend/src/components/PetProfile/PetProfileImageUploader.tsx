import { useEffect } from 'react';
import { styled } from 'styled-components';

import CameraIcon from '@/assets/svg/camera_icon.svg';
import { usePetProfileContext } from '@/context/petProfile';
import { useImageUpload } from '@/hooks/common/useImageUpload';

const PetProfileImageUploader = () => {
  const { updatePetProfile } = usePetProfileContext();
  const { previewImage, imageUrl, uploadImage } = useImageUpload();

  useEffect(() => {
    updatePetProfile({ imageUrl });
  }, [imageUrl]);

  return (
    <ImageUploadLabel aria-label="사진 업로드하기">
      <input type="file" accept="image/*" onChange={uploadImage} />
      <PreviewImageWrapper>
        {previewImage && <PreviewImage src={previewImage} alt="" />}
      </PreviewImageWrapper>
      <CameraIconWrapper>
        <img src={CameraIcon} alt="" />
      </CameraIconWrapper>
    </ImageUploadLabel>
  );
};

export default PetProfileImageUploader;

const ImageAndButtonContainer = styled.div`
  position: relative;
`;

const PreviewImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 16rem;
  height: 16rem;

  border: none;
  border-radius: 50%;
`;

const PreviewImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
  background-color: ${({ theme }) => theme.color.grey200};
`;

const ImageUploadLabel = styled.label`
  cursor: pointer;

  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 16rem;
  height: 16rem;

  background-color: #d0e6f9;
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;

  & > input {
    display: none;
  }
`;

const CameraIconWrapper = styled.div`
  position: absolute;
  right: 0;
  bottom: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 5rem;

  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;
`;
