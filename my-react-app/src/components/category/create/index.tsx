import {PhotoIcon, UserCircleIcon} from '@heroicons/react/24/solid'

const CategoryCreatePage = () => {
    const onHandleSubmit = async (e) => {
        e.preventDefault();
        console.log("----Submit form----");
    }

    console.log("----Render app----");

    return (
        <>
            <div className="py-12">
                <div className="mx-auto max-w-7xl px-6 lg:px-24">
                    <form onSubmit={onHandleSubmit}>
                        <p className="text-center text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
                            Створення категорії
                        </p>
                        <div className="space-y-12">
                            <div className="border-gray-900/10 pb-4">
                                <div className="mt-4">
                                    <div className="my-4">
                                        <label htmlFor="name"
                                               className="block text-sm font-medium leading-6 text-gray-900">
                                            Назва
                                        </label>
                                        <div className="mt-2">
                                            <input
                                                id="name"
                                                name="name"
                                                type="text"
                                                autoComplete="email"
                                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                            />
                                        </div>
                                    </div>
                                    <div className="my-4">
                                        <label htmlFor="cover-photo"
                                               className="block text-sm font-medium leading-6 text-gray-900">
                                            Оберіть фото
                                        </label>
                                        <div
                                            className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                                            <div className="text-center">
                                                <PhotoIcon aria-hidden="true"
                                                           className="mx-auto h-12 w-12 text-gray-300"/>
                                                <div className="mt-4 flex text-sm leading-6 text-gray-600">
                                                    <label
                                                        htmlFor="file-upload"
                                                        className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                                                    >
                                                        <span>Upload a file</span>
                                                        <input id="file-upload" name="file-upload" type="file"
                                                               className="sr-only"/>
                                                    </label>
                                                    <p className="pl-1">or drag and drop</p>
                                                </div>
                                                <p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to
                                                    10MB</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="my-4">
                                        <label htmlFor="about"
                                               className="block text-sm font-medium leading-6 text-gray-900">
                                            Опис
                                        </label>
                                        <div className="mt-2">
                                            <textarea
                                                id="about"
                                                name="about"
                                                rows={3}
                                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                defaultValue={''}
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div className="flex items-center justify-end gap-x-6">
                            <button type="button" className="text-sm font-semibold leading-6 text-gray-900">
                                Cancel
                            </button>
                            <button
                                type="submit"
                                className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            >
                                Save
                            </button>
                        </div>
                    </form>
                </div>
            </div>

        </>
    );
}

export default CategoryCreatePage;