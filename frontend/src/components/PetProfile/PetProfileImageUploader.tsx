/* eslint-disable react/jsx-no-useless-fragment */
import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import CameraIcon from '@/assets/svg/camera_icon.svg';
import { usePetProfileContext } from '@/context/petProfile';
import { useImageUpload } from '@/hooks/common/useImageUpload';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';

const PetProfileImageUploader = () => {
  const { updateFinalPetProfile } = useOutletContext<PetProfileOutletContextProps>();
  const { petProfile, updatePetProfile } = usePetProfileContext();
  const { previewImage, imageUrl, uploadImage, deletePreviewImage } = useImageUpload();

  useEffect(() => {
    updatePetProfile({ imageUrl });
  }, [imageUrl, updatePetProfile]);

  useEffect(() => {
    updateFinalPetProfile(petProfile);
  }, [petProfile, updateFinalPetProfile]);

  return (
    <>
      {previewImage ? (
        <ImageAndButtonContainer>
          <PreviewImageWrapper>
            <PreviewImage src={previewImage} alt="미리보기" />
          </PreviewImageWrapper>
          <ImageDeleteButton type="button" onClick={deletePreviewImage} aria-label="사진 삭제">
            x
          </ImageDeleteButton>
        </ImageAndButtonContainer>
      ) : (
        <ImageUploadLabel aria-label="사진 업로드하기">
          <input type="file" accept="image/*" onChange={uploadImage} />
          <CameraIconWrapper>
            <img src={CameraIcon} alt="" />
          </CameraIconWrapper>
        </ImageUploadLabel>
      )}
    </>
  );
};

export default PetProfileImageUploader;

const ImageAndButtonContainer = styled.div`
  position: relative;
`;

const PreviewImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 15rem;
  height: 15rem;

  background-color: ${({ theme }) => theme.color.white};
  border-radius: 50%;
`;

const PreviewImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const ImageDeleteButton = styled.button`
  cursor: pointer;

  position: absolute;
  top: 0;
  right: -1.6rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 5rem;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.color.grey600};
  text-align: center;

  background-color: transparent;
  border: none;
`;

const ImageUploadLabel = styled.label`
  cursor: pointer;

  position: relative;

  width: 15rem;
  height: 15rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border: 1px solid ${({ theme }) => theme.color.grey300};
  border-radius: 50%;

  & > input {
    opacity: 0;
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
