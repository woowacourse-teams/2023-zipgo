import { useEffect, useState } from 'react';

const usePageTitle = (initialTitle: string) => {
  const [title, setTitle] = useState(initialTitle);

  const updateTitle = () => {
    const htmlTitle = document.querySelector('title');

    if (htmlTitle) {
      htmlTitle.innerText = title;
    }
  };

  useEffect(updateTitle, [title]);

  return setTitle;
};

export default usePageTitle;
